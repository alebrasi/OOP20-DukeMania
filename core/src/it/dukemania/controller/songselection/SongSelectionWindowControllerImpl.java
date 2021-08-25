package it.dukemania.controller.songselection;

import it.dukemania.controller.logic.Columns;
import it.dukemania.controller.logic.DifficultyLevel;
import it.dukemania.controller.logic.GameUtilities;
import it.dukemania.controller.logic.GameUtilitiesImpl;
import it.dukemania.controller.logic.TrackFilter;
import it.dukemania.controller.logic.TrackFilterImpl;
import it.dukemania.model.GameModel;
import it.dukemania.model.serializers.song.SongInfo;
import it.dukemania.model.serializers.ConfigurationsModelImpl;
import it.dukemania.model.serializers.song.TrackInfo;
import it.dukemania.midi.InstrumentType;
import it.dukemania.midi.KeyboardTrack;
import it.dukemania.midi.MidiParser;
import it.dukemania.midi.ParsedTrack;
import it.dukemania.midi.Parser;
import it.dukemania.midi.Song;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactory;
import it.dukemania.util.storage.StorageFactoryImpl;
import it.dukemania.windowmanager.DukeManiaWindowState;
import it.dukemania.windowmanager.SwitchWindowNotifier;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SongSelectionWindowControllerImpl implements SongSelectionWindowController {

    private final StorageFactory storageFactory = new StorageFactoryImpl();
    private final Storage externalStorage = storageFactory.getExternalStorage();
    private final Storage configurationStorage = storageFactory.getConfigurationStorage();

    private SongInfo currentSong;
    private final List<SongInfo> songsConfigurations;

    private final GameUtilities gameUtils = new GameUtilitiesImpl();
    private final TrackFilter trackFilter = new TrackFilterImpl();

    private static final int PERCUSSION_CHANNEL = 10;
    private int selectedTrackChannel = 1;
    private final SwitchWindowNotifier switchWindowNotifier;

    private final ConfigurationsModelImpl configurationModel = new ConfigurationsModelImpl(configurationStorage);

    private String path;
    private final GameModel data;

    public SongSelectionWindowControllerImpl(final SwitchWindowNotifier notifier,
                                             final GameModel data) throws NoSuchAlgorithmException {
        songsConfigurations = getSongsConfiguration();
        this.data = data;
        this.switchWindowNotifier = notifier;
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
    public final void setSongPath(final String path) throws InvalidMidiDataException, IOException {
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
        Parser parser = MidiParser.getInstance();
        File songFile = externalStorage.getAsFile(path);
        Song s = parser.parse(songFile);
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

                                        if (t.getClass().equals(KeyboardTrack.class)) {
                                            KeyboardTrack tmp = (KeyboardTrack) t;
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
                                    .filter(t -> t.getDifficultyLevel() != DifficultyLevel.UNKNOWN)
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
    public final void setPlayTrack(final int trackNumber) {
        selectedTrackChannel = trackNumber;
    }

    @Override
    public final void updateTracks(final List<String> names, final List<InstrumentType> instruments) {
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
    public final void playSong() throws InvalidMidiDataException, IOException {
        Parser parser = MidiParser.getInstance();
        Song song = parser.parse(new File(path));
        ParsedTrack selectedTrack = trackFilter.reduceTrack(song)
                                            .stream()
                                            .filter(t -> t.getChannel() == selectedTrackChannel)
                                            .findFirst().get();
        currentSong.getTracks().forEach(x -> {
            if (x.getChannel() != 10) {
                KeyboardTrack track = (KeyboardTrack) song.getTracks()
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
        switchWindowNotifier.switchWindow(DukeManiaWindowState.PLAY, data);
    }

    @Override
    public final void setColumnsNumber(final int columns) {
        data.setNumColumns(columns);
    }

    @Override
    public final SongInfo getSongInfo() {
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
    public final String[] getAllInstruments() {
        return Arrays.stream(InstrumentType.values()).map(Enum::toString).toArray(String[]::new);
    }

    @Override
    public final Integer[] getNumOfCols() {
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

}
