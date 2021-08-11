package it.dukemania.logic;

public class NoteRange {

    private ColumnsEnum column;
    private int start;
    private int end;

    public NoteRange(final ColumnsEnum column, final int start, final int end) {
        super();
        this.column = column;
        this.start = start;
        this.end = end;
    }

    public final ColumnsEnum getColumn() {
        return column;
    }

    public final int getStart() {
        return start;
    }

    public final int getEnd() {
        return end;
    }
}
