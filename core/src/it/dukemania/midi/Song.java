package it.dukemania.midi;

import java.util.List;

public class Song {
    private final String title;
    private final double  duration;
    private final List<MyTrack> tracks;
    private final double bpm;                  //potrebbe essere un int, controllare eventuali valori di default

    public Song(final String title, final double duration, final List<MyTrack> tracks, final double bPM) {
        super();
        this.title = title;
        this.duration = duration;
        this.tracks = tracks;
        this.bpm = bPM;
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
    public final List<MyTrack> getTracks() {
        return tracks;
    }

}
