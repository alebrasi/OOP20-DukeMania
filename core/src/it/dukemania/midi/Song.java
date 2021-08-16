package it.dukemania.midi;

import java.util.List;

public class Song {
    private final String title;
    private final double  duration;
    private List<TrackInterface> tracks;
    private final double bpm;                  //potrebbe essere un int, controllare eventuali valori di default
    private final String songHash;

    public Song(final String title, final double duration, final double bPM, final String songHash) {
        super();
        this.title = title;
        this.duration = duration;
        this.bpm = bPM;
        this.songHash = songHash;
    }
    public final String getTitle() {
        return title;
    }
    public final double getBPM() {
        return bpm;
    }
    public final double getDuration() {
        return duration;
    }
    public final List<TrackInterface> getTracks() {
        return tracks;
    }

    public final void setTracks(final List<TrackInterface> myTracks) {
        this.tracks = myTracks;
    }
    public final String getSongHash() {
        return songHash;
    }

}
