package it.dukemania.Controller.logic;

import it.dukemania.midi.AbstractNote;

public class LogicNoteImpl implements LogicNote {
    private int height;
    private AbstractNote note;
    private Columns column;

    public LogicNoteImpl(final AbstractNote note, final Columns column, final int height) {
       this.note = note;
       this.column = column;
       this.height = height;
    }

    public final int getHeight() {
        return height;
    }

    public final Columns getColumn() {
        return column;
    }

    @Override
    public final long getNoteStarts() {
        return this.note.getStartTime();
    }

    @Override
    public final long getNoteDuration() {
        return Math.round(this.note.getDuration().get());
    }



}
