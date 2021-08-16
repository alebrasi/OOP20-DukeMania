package it.dukemania.Controller.logic;

import it.dukemania.midi.Note;

public class LogicNoteImpl implements LogicNote {
    private int height;
    private long noteStrarts;
    private long duration;
    private Columns column;

    public LogicNoteImpl(final Note note, final Columns column, final int height) {
       this.noteStrarts = note.getStartTime();
       this.duration = Math.round(note.getDuration().get());
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
