package it.dukemania.View.notesGraphics;

public class ComputingShiftImpl implements ComputingShift {
    private static final int NOTE_SHIFT = 35;

    //this method calculates the displacement of the position of the buttons as a function of the number of columns
    @Override
    public int calculateShifting(final int numberOfColumns) {
        return numberOfColumns == 4 || numberOfColumns == 8 ? 0 : numberOfColumns == 5 ? 20 : numberOfColumns == 7 
                ? 4 : 9;
    }

    @Override
    public int getNoteShift() {
        return this.NOTE_SHIFT;
    }

}
