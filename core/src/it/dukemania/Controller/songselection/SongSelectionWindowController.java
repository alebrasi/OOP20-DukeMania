package it.dukemania.Controller.songselection;

import it.dukemania.Model.serializers.song.SongInfo;
import it.dukemania.Model.serializers.song.TrackInfo;
import it.dukemania.midi.InstrumentType;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.util.List;

public interface SongSelectionWindowController {
    /*
    TODO:
        - Select song
        - Get Tracks
        - Get Instruments
        - Get instrument associated to the track
        - Set instrument to a track (Optional)
        - Set play track
        - Play!
    */
    void openSong(String path) throws InvalidMidiDataException, IOException;
    void setPlayTrack(int trackNumber);
    void updateTracks(List<String> names, List<InstrumentType> instruments);
    void playSong();
    SongInfo getSongInfo();
    String[] getAllInstruments();
}
