package it.dukemania.audioengine;


public interface Player {

    /**
     * A PlayableTrack represents a track as an Observable, with the method update, all the tracks update their status.
     */
    interface PlayableTrack {
        boolean hasNext();
        void update(long millis);
    }

    /**
     * Checks and plays all the notes whose startTime is inferiors than the total milliseconds
     * that have passed from the total elapsed time,
     * then plays a buffer in the AudioEngine.
     * @return true if the song is over, false otherwise
     */
     boolean playNotes();
}
