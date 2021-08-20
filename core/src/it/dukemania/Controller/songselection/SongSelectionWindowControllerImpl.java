package it.dukemania.Controller.songselection;

import it.dukemania.Controller.logic.*;
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
import javax.sound.midi.Track;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;


/*
TODO:
    - Load song configs and save..
    - Load synthesizers presets
    - Check what to do for the digest try catch
 */

public class SongSelectionWindowControllerImpl implements SongSelectionWindowController {

    private final StorageFactory storageFactory = new StorageFactoryImpl();
    private final Storage externalStorage = storageFactory.getExternalStorage();
    private final Storage configurationStorage = storageFactory.getConfigurationStorage();

    private SongInfo currentSong;
    private final List<SongInfo> songsConfigurations;
    private final List<SynthInfo> synthesizersPresets;

    private final GameUtilities gameUtils = new GameUtilitiesImpl();
    private final TrackFilter trackFilter = new TrackFilterImpl();

    private static final int PERCUSSION_CHANNEL = 10;
    private int selectedTrackChannel = 0;

    private final ConfigurationsModelImpl configurationModel = new ConfigurationsModelImpl(configurationStorage);

    private String path;
    private Song selectedSong;

    public SongSelectionWindowControllerImpl() throws NoSuchAlgorithmException {
        synthesizersPresets = readSynthPresets();
        songsConfigurations = getSongsConfiguration();
    }

    @Override
    public void openSong(final String path) throws InvalidMidiDataException, IOException {
        selectedTrackChannel = 0;
        byte[] fileBytes = externalStorage.readFileAsByte(path);
        String hashedFile = getHashString(fileBytes);

        //Find the song configuration that matches the digest
        Optional<SongInfo> song = songsConfigurations.stream().filter(s -> s.getSongHash().equals(hashedFile)).findFirst();
        this.path = path;
        if (song.isPresent()) {
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
                                            instrument = (InstrumentType) ((TrackImpl) t).getInstrument();
                                            trackName = ((TrackImpl) t).getInstrument().toString();
                                            difficulty = difficulties.get(t.getChannel());
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
        selectedTrackChannel = trackNumber + 1;
        System.out.println(trackNumber);
    }

    @Override
    public void updateTracks(final List<String> names, final List<InstrumentType> instruments) {
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
        notifier.switchWindow(DukeManiaWindowState.PLAY, new Object[]{song, selectedTrack, currentSong.getSongHash()});
    }

    @Override
    public SongInfo getSongInfo() {
        currentSong.getTracks().forEach(t -> System.out.println(t.getDifficultyLevel().getEffectiveName()));
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

    private List<SongInfo> getSongsConfiguration() {
        //ConfigurationsModelImpl.Song conf = new ConfigurationsModelImpl.Song();
        List<SongInfo> songs = Collections.emptyList();
        try {
            songs = configurationModel.readSongsConfiguration();
        } catch (IOException e) {
            //e.printStackTrace();
            return new ArrayList<>();
        }
        return songs;
    }

    private void writeSongsConfiguration(final List<SongInfo> songs) {
        /*
        List<TrackInfo> tracks = new ArrayList<>();
        List<SongInfo> mySongs = new ArrayList<>();
        tracks.add(new TrackInfo(1, "Slap Bass", InstrumentType.ELECTRIC_GUITAR_C));
        tracks.add(new TrackInfo(2, "Guitar Solo", InstrumentType.ELECTRIC_GUITAR_C));
        mySongs.add(new SongInfo("This game", "8e155c5c5b4b0b2c2cbedbcdcc58d59859cf493aa67d6a26e8d079872d062f9b", 3, tracks, 160));
        mySongs.add(new SongInfo("Red Zone", "agdgggdgfg", 3, tracks, 180));
        */

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
