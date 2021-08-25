package it.dukemania.midi;

import java.util.List;

public abstract class ParsedTrack {
    private final List<AbstractNote> notes;
    private final int channel;

    /**
     * constructor.
     * @param notes the list of notes which compose the track
     * @param channel the MIDI channel
     */
    public ParsedTrack(final List<AbstractNote> notes, final int channel) {
        this.notes = notes;
        this.channel = channel;
    }
    /**
     * this method return the list of the notes that compose the track.
     * @return the list of notes that compose the track
     */
    public final List<AbstractNote> getNotes() {
        return notes;
    }

    /**
     * this method return the channel number (starting from 1).
     * @return the MIDI channel which can be used as identifier for each ParsedTrack in a song
     */
    public final int getChannel() {
        return channel;
    }

    /**
     * this method delete a note from the track.
     * @param note the not to delete
     */
    public final void deleteNote(final AbstractNote note) {
        this.notes.remove(note);
    }

}
