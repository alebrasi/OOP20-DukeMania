package it.dukemania.midi;

import java.util.List;

public class PercussionTrack implements TrackInterface {
    protected final List<AbstractNote> notes;
    private final int channel;

    public PercussionTrack(final List<AbstractNote> notes, final int channel) {
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
