package it.dukemania.View.notesGraphics;

public interface ComputingShift {

    /**
     * @param nummberOfColumns
     * @return the displacement of the position of the buttons as a function of the number of columns
     */
    int calculateShifting(int nummberOfColumns);

    /***
     * 
     * @return the note shift
     */
    int getNoteShift();

    /***
     * 
     * @return the height of the spark
     */
    int getSparksHeight();

    /***
     * 
     * @return the height of the scoreboard
     */
    int getScoreboardHeight();

    /***
     * 
     * @return the font accuracy
     */
    float getFontAccuracy();

    /***
     * 
     * @return horizontal spark shift
     */
    int getHorizontalSparkShift();

    /***
     * 
     * @return the vertical dimension of the spark
     */
    int getySpark();

    /***
     * 
     * @return the duration offset related to the graphic
     */
    int getDurationOffset();

    /***
     * 
     * @return the offset related to the beginning of the note
     */
    int getNoteStartOffset();

}
