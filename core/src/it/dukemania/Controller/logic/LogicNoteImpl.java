package it.dukemania.Controller.logic;

import it.dukemania.midi.AbstractNote;
import it.dukemania.midi.Note;

public class LogicNoteImpl implements LogicNote {
    private int height;
    private long noteStrarts;
    private long duration;
    private Columns column;

    public LogicNoteImpl(final AbstractNote y, final Columns column, final int height) {
       this.noteStrarts = y.getStartTime();
       this.duration = Math.round(y.getDuration().get());
       this.column = column;
       this.height = height;
    }

    public final int getHeight() {
        return height;
    }

    public final long getNoteStrarts() {
        return noteStrarts;
    }

    public final long getDuration() {
        return duration;
    }

    public final Columns getColumn() {
        return column;
    }

}
