package it.dukemania.controller.logic;

import it.dukemania.midi.AbstractNote;

public class LogicNoteImpl implements LogicNote {
    private final int height;
    private final AbstractNote note;
    private final Columns column;

    public LogicNoteImpl(final AbstractNote note, final Columns column, final int height) {
       this.note = note;
       this.column = column;
       this.height = height;
    }

    @Override
    public final int getHeight() {
        return height;
    }


    @Override
    public final Columns getColumn() {
        return column;
    }

    @Override
    public final long getNoteStarts() {
        return this.note.getStartTime();
    }

    @Override
    public final Long getNoteDuration() {
        return this.note.getDuration().orElse(0L);
    }




}


