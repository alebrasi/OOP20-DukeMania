package it.dukemania.audioengine;

import it.dukemania.midi.AbstractNote;

public interface Player {

    /**
     * A PlayableTrack rapresents a track as an Observable, with the method update, all the tracks update their status.
     * @param <AbstractNote> AbstractNote contains the information of the Note to play
     */
    interface PlayableTrack<AbstractNote> {
        boolean hasNext();
        void update(long millis);
    }

    /**
     * Checks and plays all the notes whose startTime is inferios than the total milliseconds
     * that have passed from the total elapsed time,
     * then plays a buffer in the AudioEngine.
     */
     void playNotes();
}
