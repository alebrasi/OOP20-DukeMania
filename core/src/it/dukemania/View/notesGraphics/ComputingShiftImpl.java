package it.dukemania.View.notesGraphics;

public class ComputingShiftImpl implements ComputingShift {
    private static final int NOTE_SHIFT = 35;
    private static final int SPARKS_HEIGHT = 40;
    private static final int SCOREBOARD_HEIGHT = 80;
    private static final float FONT_ACCURACY = 0.75f;

    //this method calculates the displacement of the position of the buttons as a function of the number of columns
    @Override
    public int calculateShifting(final int numberOfColumns) {
        return numberOfColumns == 4 || numberOfColumns == 8 ? 0 : numberOfColumns == 5 ? 20 : numberOfColumns == 7 
                ? 4 : 9;
    }

    @Override
    public int getNoteShift() {
        return ComputingShiftImpl.NOTE_SHIFT;
    }

    @Override
    public int getSparksHeight() {
        return ComputingShiftImpl.SPARKS_HEIGHT;
    }

    @Override
    public int getScoreboardHeight() {
        return ComputingShiftImpl.SCOREBOARD_HEIGHT;
    }

    @Override
    public float getFontAccuracy() {
        return ComputingShiftImpl.FONT_ACCURACY;
    }

}
