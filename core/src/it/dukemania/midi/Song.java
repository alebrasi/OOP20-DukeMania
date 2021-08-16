package it.dukemania.midi;

import java.util.Collection;

public class Song {
	private final String title;
    private final double  duration;
    private final Collection<MyTrack> tracks;
    private final double bpm;                  //potrebbe essere un int, controllare eventuali valori di default

    public Song(final String title, final double duration, final Collection<MyTrack> tracks, final double bPM) {
        super();
        this.title = title;
        this.duration = duration;
        this.tracks = tracks;
        bpm = bPM;
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
    public final Collection<MyTrack> getTracks() {
        return tracks;
    }

}
