package it.dukemania.Controller.logic;

public interface ScoreStrategy {

    /**
     * 
     * @param column the column of the note
     * @param start the start time of the note in milliseconds
     * @param end the end time of the note in milliseconds
     * @param currentRange the range used for calculating the score
     * @param columnNumber the number of colums used for the current track
     * @return a pair composed by the score and the range used
     */
    int scoreCalculation(Columns column, long start, long end, NoteRange currentRange, int columnNumber);
}
