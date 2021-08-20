package it.dukemania.audioengine;

import it.dukemania.midi.AbstractNote;
import it.dukemania.midi.Song;

import java.time.Instant;
import java.util.Iterator;

public interface Player {

    /**
     * A NoteIterator rapresents a track as an Iterator, with the methods to get the current note waiting to be played
     * and to skip ti the next note.
     * @param <Note> We use Note because we want to see when a note has to be played.
     */
    interface NoteIterator<Note> {
        Note current();
        boolean hasNext();
        void increment();
        int getChannel();
    }

    /**
     * Tells if the song has finished to play.
     * @return true if the song has ended, false otherwise
     */
    boolean hasSongEnded();

    /**
     * Checks and plays all the notes whose startTime is inferios than the total milliseconds that have passed from the total elapsed time,
     * then plays a buffer in the AudioEngine.
     */
     void playNotes();
}
