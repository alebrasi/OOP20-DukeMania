package it.dukemania.View.notesGraphics;

import it.dukemania.Controller.logic.Columns;

public class ComputingShiftImpl implements ComputingShift {
    private static final int NOTE_SHIFT = 30;
    private static final int SPARK_SPACE = 40;
    private static final int SCOREBOARD_HEIGHT = 80;
    private static final float FONT_ACCURACY = 0.75f;
    private static final int HORIZONTAL_SPARK_SHIFT = 25;
    private static final int SPARK_HEIGHT = 70;
    private static final int DURATION_OFFSET = 5500;
    private static final int NOTE_START_OFFSET = 2500;
    private static final int SHIFT_5_COLUMN = 20;
    private static final int SHIFT_6_COLUMN = 9;


    //this method calculates the displacement of the position of the buttons as a function of the number of columns
    @Override
    public final int calculateShifting(final int numberOfColumns) {
        return numberOfColumns == 4 || numberOfColumns == 8 ? 0 : numberOfColumns == Columns.COLUMN_5.getNumericValue() 
                ? ComputingShiftImpl.SHIFT_5_COLUMN : numberOfColumns == Columns.COLUMN_7.getNumericValue() 
                ? 4 : ComputingShiftImpl.SHIFT_6_COLUMN;
    }

    @Override
    public final int getNoteShift() {
        return ComputingShiftImpl.NOTE_SHIFT;
    }

    @Override
    public final int getSparksHeight() {
        return ComputingShiftImpl.SPARK_SPACE;
    }

    @Override
    public final int getScoreboardHeight() {
        return ComputingShiftImpl.SCOREBOARD_HEIGHT;
    }

    @Override
    public final float getFontAccuracy() {
        return ComputingShiftImpl.FONT_ACCURACY;
    }

    @Override
    public final int getHorizontalSparkShift() {
        return ComputingShiftImpl.HORIZONTAL_SPARK_SHIFT;
    }

    @Override
    public final int getySpark() {
        return ComputingShiftImpl.SPARK_HEIGHT;
    }

    @Override
    public final int getDurationOffset() {
        return ComputingShiftImpl.DURATION_OFFSET;
    }

    @Override
    public final int getNoteStartOffset() {
        return ComputingShiftImpl.NOTE_START_OFFSET;
    }


}
