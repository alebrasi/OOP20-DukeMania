package it.dukemania.midi;

import java.util.List;

public class Song {
    private final String title;
    private final double  duration;
    private final List<MidiTrack> tracks;
    private final double bpm;                  //potrebbe essere un int, controllare eventuali valori di default


    public Song(final String title, final double duration, final List<MidiTrack> tracks, final double bPM) {
        super();
        this.title = title;
        this.duration = duration;
        this.bpm = bPM;
        this.tracks = tracks;
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
    public final List<MidiTrack> getTracks() {
        return tracks;
    }

}
