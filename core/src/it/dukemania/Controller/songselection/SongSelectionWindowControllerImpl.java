package it.dukemania.Controller.songselection;

import it.dukemania.Model.serializers.song.SongInfo;
import it.dukemania.Model.serializers.Configuration;
import it.dukemania.Model.serializers.song.TrackInfo;
import it.dukemania.Model.serializers.synthesizer.SynthInfo;
import it.dukemania.audioengine.*;
import it.dukemania.midi.InstrumentType;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactoryImpl;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SongSelectionWindowControllerImpl implements SongSelectionWindowController {

    private final Storage storage = new StorageFactoryImpl().getExternalStorage();
    private final MessageDigest digest = MessageDigest.getInstance("SHA-256");
    private final List<SongInfo> songs = getSongsConfiguration();
    private final List<SynthInfo> synthesizersPresets = readSynth();
    private SongInfo currentSong;

    public SongSelectionWindowControllerImpl() throws NoSuchAlgorithmException {

    }

    @Override
    public void openSong(final String path) {
        byte[] fileBytes = new byte[0];
        try {
            fileBytes = storage.readFileAsByte(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String hashedFile = getHashString(fileBytes);
        Optional<SongInfo> song = songs.stream().filter(s -> s.getSongHash().equals(hashedFile)).findFirst();
        song.ifPresent(songInfo -> currentSong = songInfo);
        songs.remove(currentSong);
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
        System.out.println(trackNumber);
    }

    @Override
    public void updateTracks(final List<String> names, final List<InstrumentType> instruments) {
        TrackInfo[] tracks = currentSong.getTracks().toArray(TrackInfo[]::new);
        for (int i = 0; i < names.size(); i++) {
            tracks[i].setTrackName(names.get(i));
            tracks[i].setInstrument(instruments.get(i));
        }
        songs.add(currentSong);
        writeSongsConfiguration(songs);
    }

    @Override
    public List<TrackInfo> getTracks() {
        return currentSong.getTracks();
    }

    @Override
    public String[] getAllInstruments() {
        readSynth();
        return Arrays.stream(InstrumentType.values()).map(Enum::toString).toArray(String[]::new);
    }

    private List<SongInfo> getSongsConfiguration() {
        Configuration.song conf = new Configuration.song();
        List<SongInfo> songs = Collections.emptyList();
        try {
            songs = conf.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }

    private void writeSongsConfiguration(final List<SongInfo> songs) {
        List<TrackInfo> tracks = new ArrayList<>();
        List<SongInfo> mySongs = new ArrayList<>();
        tracks.add(new TrackInfo(1, "Slap Bass", InstrumentType.ELECTRIC_GUITAR_C));
        tracks.add(new TrackInfo(2, "Guitar Solo", InstrumentType.ELECTRIC_GUITAR_C));
        mySongs.add(new SongInfo("This game", "8e155c5c5b4b0b2c2cbedbcdcc58d59859cf493aa67d6a26e8d079872d062f9b", 3, tracks, 160));
        mySongs.add(new SongInfo("Red Zone", "agdgggdgfg", 3, tracks, 180));

        Configuration.song conf = new Configuration.song();
        try {
            conf.writeAll(songs);
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
        asd.add(new SynthInfo("amonger", b));
        asd.add(new SynthInfo("sas", c));


        Configuration.synthesizer g = new Configuration.synthesizer();
        try {
            g.writeAll(asd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<SynthInfo> readSynth() {
        Configuration.synthesizer conf = new Configuration.synthesizer();
        List<SynthInfo> synthesizers = Collections.emptyList();
        try {
            synthesizers = conf.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return synthesizers;
    }

}
