package it.dukemania.midi;

import java.util.List;

public interface MidiTrack {

    /**
     * this method return the list of the notes that compose the track.
     * @return notes
     */
    List<AbstractNote> getNotes();

    /**
     * this method return the channel number (starting from 1).
     * @return channel
     */
    int getChannel();

    /**
     * this method delete a note from the track.
     * @param note
     */
    void deleteNote(AbstractNote note);

}
