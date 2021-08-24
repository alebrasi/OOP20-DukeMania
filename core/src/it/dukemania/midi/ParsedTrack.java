package it.dukemania.midi;

import java.util.List;

public abstract class ParsedTrack {
    private final List<AbstractNote> notes;
    private final int channel;

    /**
     * constructor.
     * @param notes
     * @param channel
     */
    public ParsedTrack(final List<AbstractNote> notes, final int channel) {
        this.notes = notes;
        this.channel = channel;
    }
    /**
     * this method return the list of the notes that compose the track.
     * @return notes
     */
    public final List<AbstractNote> getNotes() {
        return notes;
    }

    /**
     * this method return the channel number (starting from 1).
     * @return channel
     */
    public final int getChannel() {
        return channel;
    }

    /**
     * this method delete a note from the track.
     * @param note
     */
    public final void deleteNote(final AbstractNote note) {
        this.notes.remove(note);
    }

}
