package it.dukemania.midi;

import java.util.List;

public class PercussionTrackImpl implements ParsedTrack {
    private final List<AbstractNote> notes;
    private final int channel;

    /**
     * constructor.
     * @param notes
     * @param channel
     */
    public PercussionTrackImpl(final List<AbstractNote> notes, final int channel) {
        this.notes = notes;
        this.channel = channel;
    }

    @Override
    public final List<AbstractNote> getNotes() {
        return notes;
    }

    @Override
    public final int getChannel() {
        return channel;
    }

    @Override
    public final void deleteNote(final AbstractNote note) {
        this.notes.remove(note);
    }

}
