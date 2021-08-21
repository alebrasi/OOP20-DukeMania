package it.dukemania.Controller.logic;

public class NoteRange {

    private Columns column;
    private long start;
    private long end;

    public NoteRange(final Columns column, final long start, final long end) {
        super();
        this.column = column;
        this.start = start;
        this.end = end;
    }

    public final Columns getColumn() {
        return column;
    }

    public final long getStart() {
        return start;
    }

    public final long getEnd() {
        return end;
    }
}
