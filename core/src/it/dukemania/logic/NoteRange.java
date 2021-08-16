package it.dukemania.logic;

public class NoteRange {

    private Columns column;
    private int start;
    private int end;

    public NoteRange(final Columns column, final int start, final int end) {
        super();
        this.column = column;
        this.start = start;
        this.end = end;
    }

    public final Columns getColumn() {
        return column;
    }

    public final int getStart() {
        return start;
    }

    public final int getEnd() {
        return end;
    }
}
