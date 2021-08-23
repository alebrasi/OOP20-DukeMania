package it.dukemania.midi;

import java.util.List;

public class Song {
    private final String title;
    private final double  duration;
    private final List<MidiTrack> tracks;
    private final double bpm;                  //potrebbe essere un int, controllare eventuali valori di default


    /**
     * constructor.
     * @param title
     * @param duration
     * @param tracks
     * @param bPM
     */
    public Song(final String title, final double duration, final List<MidiTrack> tracks, final double bPM) {
        super();
        this.title = title;
        this.duration = duration;
        this.bpm = bPM;
        this.tracks = tracks;
    }

    /**
     * this method return the song title based on the MIDI file name.
     * @return String title
     */
    public final String getTitle() {
        return title;
    }

    /**
     * this method return the BPM of the song.
     * @return double bpm
     */
    public final double getBPM() {
        return bpm;
    }

    /**
     * this method return the song duration in microseconds.
     * @return double duration
     */
    public final double getDuration() {
        return duration;
    }

    /**
     * this method return the tracks that compose the song.
     * @return a list of MidiTracks 
     */
    public final List<MidiTrack> getTracks() {
        return tracks;
    }

}
