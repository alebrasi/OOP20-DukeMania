package it.dukemania.midi;

import java.util.List;


public class Song {
    private final String title;
    private final double  duration;
    private final List<ParsedTrack> tracks;
    private final double bpm;                  //potrebbe essere un int, controllare eventuali valori di default


    /**
     * constructor.
     * @param title the title of the song
     * @param duration the duration of the song expressed in microseconds
     * @param tracks the list of the ParsedTrack that compose the song
     * @param bpm the song BPM (Beat Per Minute)
     */
    public Song(final String title, final double duration, final List<ParsedTrack> tracks, final double bpm) {
        super();
        this.title = title;
        this.duration = duration;
        this.bpm = bpm;
        this.tracks = tracks;
    }

    /**
     * this method return the song title.
     * @return the title of the song
     */
    public final String getTitle() {
        return title;
    }

    /**
     * this method return the BPM of the song.
     * @return the song BPM
     */
    public final double getBPM() {
        return bpm;
    }

    /**
     * this method return the song duration in microseconds.
     * @return the song duration in microseconds
     */
    public final double getDuration() {
        return duration;
    }

    /**
     * this method return the tracks that compose the song.
     * @return a list of the ParsedTrack which compose the song 
     */
    public final List<ParsedTrack> getTracks() {
        return tracks;
    }

}
