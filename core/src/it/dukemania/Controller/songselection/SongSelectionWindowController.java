package it.dukemania.Controller.songselection;

import it.dukemania.Model.serializers.song.TrackInfo;
import it.dukemania.midi.InstrumentType;

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
    void openSong(String path);
    void setPlayTrack(int trackNumber);
    void updateTracks(List<String> names, List<InstrumentType> instruments);
    List<TrackInfo> getTracks();
    String[] getAllInstruments();
}
