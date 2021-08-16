package it.dukemania.Controller.logic;

public class NoteRange {

    private Columns column;
    private long start;
    private long end;

    public NoteRange(final Columns column, final long l, final long m) {
        super();
        this.column = column;
        this.start = l;
        this.end = m;
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
