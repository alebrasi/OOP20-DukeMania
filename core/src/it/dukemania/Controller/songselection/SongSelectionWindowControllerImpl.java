package it.dukemania.Controller.songselection;

import it.dukemania.Model.InstrumentType;
import it.dukemania.Model.MyTrack;
import it.dukemania.Model.Song;
import it.dukemania.Model.TrackInfo;
import it.dukemania.Model.serializers.Configuration;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactoryImpl;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class SongSelectionWindowControllerImpl implements SongSelectionWindowController {

    private final Storage storage = new StorageFactoryImpl().getExternalStorage();
    private final MessageDigest digest = MessageDigest.getInstance("SHA-256");
    private final List<Song> songs = getSongsConfiguration();
    private List<MyTrack> currentTracks;

    public SongSelectionWindowControllerImpl() throws NoSuchAlgorithmException {

    }

    @Override
    public void openSong(final String path) {
        currentTracks = Collections.emptyList();
        byte[] fileBytes = new byte[0];
        try {
            fileBytes = storage.readFileAsByte(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String hashedFile = getHashString(fileBytes);
        Optional<Song> song = songs.stream().filter(s -> s.getSongHash().equals(hashedFile)).findFirst();
        if (song.isPresent()) {
            Song s = song.get();
            currentTracks = (List<MyTrack>) s.getTracks();
        }
    }

    private String getHashString(final byte[] bytes) {
        StringBuilder stringHash = new StringBuilder();
        byte[] d = digest.digest(bytes);
        //Convert each byte to its relative hex value (in String format)
        for (byte b : d) {
            stringHash.append(String.format("%02x", b));
        }
        return stringHash.toString();
    }

    @Override
    public void setPlayTrack(final int trackNumber) {

    }

    @Override
    public List<TrackInfo> getTracks() {
        return currentTracks
                .stream()
                .map(t -> new TrackInfo(t.getChannel(), t.getName(), t.getInstrument().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public String[] getAllInstruments() {
        return Arrays.stream(InstrumentType.values()).map(Enum::toString).toArray(String[]::new);
    }

    private List<Song> getSongsConfiguration() {
        Configuration.song d = new Configuration.song();
        List<Song> f = Collections.emptyList();
        try {
            f = d.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    private void writeSongsConfiguration(final List<Song> songs) {
        List<MyTrack> tracks = new ArrayList<>();
        List<Song> mySongs = new ArrayList<>();
        tracks.add(new MyTrack("Slap Bass", InstrumentType.SLAP_BASS_1, null, 1));
        tracks.add(new MyTrack("Guitar Solo", InstrumentType.ELECTRIC_GUITAR_C, null, 2));
        mySongs.add(new Song("This game", "asasdasdasdad", 3, tracks, 160));
        mySongs.add(new Song("Red Zone", "agdgggdgfg", 3, tracks, 180));

        Configuration.song s = new Configuration.song();
        try {
            s.writeAll(songs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
