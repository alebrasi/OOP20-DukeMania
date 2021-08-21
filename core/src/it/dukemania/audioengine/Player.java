package it.dukemania.audioengine;

import it.dukemania.midi.AbstractNote;
import it.dukemania.midi.Song;

import java.time.Instant;
import java.util.Iterator;

public interface Player {

    interface PlayableTrack<AbstractNote> {
        boolean hasNext();
        void update(long millis);
    }

    /**
     * Checks and plays all the notes whose startTime is inferios than the total milliseconds that have passed from the total elapsed time,
     * then plays a buffer in the AudioEngine.
     */
     void playNotes();
}
