package it.dukemania.Controller.songselection;

import com.badlogic.gdx.files.FileHandle;
import it.dukemania.Controller.logic.*;
import it.dukemania.Model.GameModel;
import it.dukemania.Model.serializers.song.SongInfo;
import it.dukemania.Model.serializers.ConfigurationsModelImpl;
import it.dukemania.Model.serializers.song.TrackInfo;
import it.dukemania.Model.serializers.synthesizer.SynthInfo;
import it.dukemania.audioengine.*;
import it.dukemania.midi.*;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactory;
import it.dukemania.util.storage.StorageFactoryImpl;
import it.dukemania.windowmanager.DukeManiaWindowState;
import it.dukemania.windowmanager.SwitchWindowNotifier;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;


/*
TODO:
    - Load synthesizers presets
    - Check what to do for the digest try catch
 */

public class SongSelectionWindowControllerImpl implements SongSelectionWindowController {

    private final StorageFactory storageFactory = new StorageFactoryImpl();
    private final Storage externalStorage = storageFactory.getExternalStorage();
    private final Storage configurationStorage = storageFactory.getConfigurationStorage();
    private final Storage assetStorage = storageFactory.getAssetStorage();

    private SongInfo currentSong;
    private final List<SongInfo> songsConfigurations;
    private final List<SynthInfo> synthesizersPresets;

    private final GameUtilities gameUtils = new GameUtilitiesImpl();
    private final TrackFilter trackFilter = new TrackFilterImpl();

    private static final int PERCUSSION_CHANNEL = 10;
    private int selectedTrackChannel = 1;

    private final ConfigurationsModelImpl configurationModel = new ConfigurationsModelImpl(configurationStorage);

    private String path;
    private Song selectedSong;
    private final GameModel data;

    public SongSelectionWindowControllerImpl(final GameModel data) throws NoSuchAlgorithmException {
        //TODO De-HardCode
        if (!configurationStorage.getAsFile("configs/synthesizers_config.json").exists()) {
            try {
                assetStorage.copyTo("synthesizers_config.json", configurationStorage.getBaseDirectoryName() + File.separator + "configs/synthesizers_config.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        synthesizersPresets = readSynthPresets();
        songsConfigurations = getSongsConfiguration();
        this.data = data;

        /*
        URI uri = null;
        Path myPath = null;
        try {
            uri = getClass().getResource("/").toURI();
            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                myPath = fileSystem.getPath("/");
            } else {
                myPath = Paths.get(uri);
            }
            Files.walk(myPath, 1).forEach(s -> System.out.println(s));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

         */
    }

    @Override
    public void openSong(final String path) throws InvalidMidiDataException, IOException {
        selectedTrackChannel = 1;
        byte[] fileBytes = externalStorage.readFileAsByte(path);
        String hashedFile = getHashString(fileBytes);

        //Find the song configuration that matches the digest
        Optional<SongInfo> song = songsConfigurations.stream().filter(s -> s.getSongHash().equals(hashedFile)).findFirst();
        this.path = path;
        if (song.isPresent()) {
            if (song.get().getTracks().size() == 0) {
                throw new InvalidMidiDataException();
            }
            currentSong = song.get();
            songsConfigurations.remove(currentSong);
        } else {
            createConfig(path);
        }
    }

    private void createConfig(final String path) throws InvalidMidiDataException, IOException {
        MidiParser parser = new MidiParserImpl();
        File songFile = externalStorage.getAsFile(path);
        Song s = parser.parseMidi(songFile);
        String fileHash = getHashString(externalStorage.readFileAsByte(path));

        //Maps the difficulty level to the associated track channel
        Map<Integer, DifficultyLevel> difficulties = gameUtils.generateTracksDifficulty(trackFilter.reduceTrack(s))
                                                .entrySet()
                                                .stream()
                                                .map(t -> Map.entry(t.getKey().getChannel(), t.getValue()))
                                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        //Generate a list of TrackInfo from the list of MidiTrack parsed by the MIDI parser
        List<TrackInfo> tracks = s.getTracks()
                                    .stream()
                                    .map(t -> {
                                        InstrumentType instrument = InstrumentType.APPLAUSE;
                                        String trackName = "Percussion";
                                        DifficultyLevel difficulty = DifficultyLevel.UNKNOWN;

                                        if (t.getClass().equals(TrackImpl.class)) {
                                            TrackImpl tmp = (TrackImpl) t;
                                            Enum<InstrumentType> tmpIns = tmp.getInstrument();
                                            instrument = tmpIns == null ? InstrumentType.ACOUSTIC_GRAND_PIANO
                                                                            : (InstrumentType) tmpIns;
                                            trackName = instrument.toString();
                                            difficulty = difficulties.get(tmp.getChannel());
                                        }
                                        return new TrackInfo(t.getChannel(),
                                                            trackName,
                                                            instrument,
                                                            difficulty);
                                    })
                                    .collect(Collectors.toList());
        this.currentSong = new SongInfo(s.getTitle(), fileHash, s.getDuration(), tracks, Math.round(s.getBPM()));
    }

    private String getHashString(final byte[] bytes)  {
        StringBuilder stringHash = new StringBuilder();
        byte[] d = new byte[0];
        try {
            d = MessageDigest.getInstance("SHA-256").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //Convert each byte to its relative hex value (in String format)
        for (byte b : d) {
            stringHash.append(String.format("%02x", b));
        }
        return stringHash.toString();
    }

    @Override
    public void setPlayTrack(final int trackNumber) {
        selectedTrackChannel = trackNumber;
    }

    @Override
    public void updateTracks(final List<String> names, final List<InstrumentType> instruments) {
        songsConfigurations.removeIf(x -> x.getSongHash().equals(currentSong.getSongHash()));
        TrackInfo[] tracks = currentSong.getTracks().toArray(TrackInfo[]::new);
        for (int i = 0; i < names.size(); i++) {
            tracks[i].setTrackName(names.get(i));
            tracks[i].setInstrument(instruments.get(i));
        }
        songsConfigurations.add(currentSong);
        writeSongsConfiguration(songsConfigurations);
    }

    @Override
    public void playSong(final SwitchWindowNotifier notifier) throws InvalidMidiDataException, IOException {
        MidiParser parser = new MidiParserImpl();
        Song song = parser.parseMidi(new File(path));
        MidiTrack selectedTrack = trackFilter.reduceTrack(song)
                                            .stream()
                                            .filter(t -> t.getChannel() == selectedTrackChannel)
                                            .findFirst().get();
        currentSong.getTracks().forEach(x -> {
            if (x.getChannel() != 10) {
                TrackImpl track = (TrackImpl) song.getTracks()
                                                    .stream()
                                                    .filter(trk -> trk.getChannel() == x.getChannel())
                                                    .findFirst()
                                                    .get();
                track.setInstrument((InstrumentType) x.getInstrument());
            }
        });
        data.setSelectedSong(song);
        data.setSelectedTrack(selectedTrack);
        data.setSongHash(currentSong.getSongHash());
        notifier.switchWindow(DukeManiaWindowState.PLAY, data);
    }

    @Override
    public void setColumnsNumber(final int columns) {
        data.setNumColumns(columns);
    }

    @Override
    public SongInfo getSongInfo() {
        return new SongInfo(currentSong.getTitle(),
                            "",
                            currentSong.getDuration(),
                            currentSong.getTracks()
                                    .stream()
                                    .filter(t -> t.getChannel() != PERCUSSION_CHANNEL)
                                    .collect(Collectors.toList()),
                            currentSong.getBPM());
    }

    @Override
    public String[] getAllInstruments() {
        readSynthPresets();
        return Arrays.stream(InstrumentType.values()).map(Enum::toString).toArray(String[]::new);
    }

    @Override
    public Integer[] getNumOfCols() {
        return Arrays.stream(Columns.values()).map(Columns::getNumericValue).filter(c -> c >= 4).toArray(Integer[]::new);
    }

    private List<SongInfo> getSongsConfiguration() {
        List<SongInfo> songs;
        try {
            songs = configurationModel.readSongsConfiguration();
        } catch (IOException e) {
            //e.printStackTrace();
            return new ArrayList<>();
        }
        return songs;
    }

    private void writeSongsConfiguration(final List<SongInfo> songs) {
        try {
            configurationModel.writeSongsConfiguration(songs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSynthesizers() {
        SynthBuilderImpl b = new SynthBuilderImpl();
        b.setEnveloper(new Enveloper(10l, 1f, 100l));
        b.setWavetables(new WaveTable[]{WaveTable.Square});
        b.setOffsets(new double[]{1f});
        b.setNoteLFO(LFOFactory.squareLFO(2f,1f,200));

        SynthBuilderImpl c = new SynthBuilderImpl();
        c.setEnveloper(new Enveloper(10l, 1f, 100l));
        c.setWavetables(new WaveTable[]{WaveTable.Triangle});
        c.setOffsets(new double[]{1f});
        c.setNoteLFO(LFOFactory.squareLFO(2f,1f,200));
        c.setVolumeLFO(LFOFactory.sineLFO(1f,0.1f, 200));

        List<SynthInfo> asd = new ArrayList<>();
        asd.add(new SynthInfo("amonger", b, List.of(InstrumentType.ACOUSTIC_GUITAR_S)));
        asd.add(new SynthInfo("sas", c, List.of(InstrumentType.AGOGO)));

        /*
        ConfigurationsModelImpl.Synthesizer g = new ConfigurationsModelImpl.Synthesizer();
        try {
            g.writeAll(asd);
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
    }

    private List<SynthInfo> readSynthPresets() {
        List<SynthInfo> synthesizers = Collections.emptyList();
        try {
            synthesizers = configurationModel.readSynthesizersConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return synthesizers;
    }

}
