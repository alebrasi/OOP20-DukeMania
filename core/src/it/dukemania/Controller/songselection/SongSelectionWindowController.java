package it.dukemania.Controller.songselection;

import it.dukemania.Model.TrackInfo;

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
    List<TrackInfo> getTracks();
    String[] getAllInstruments();
}
