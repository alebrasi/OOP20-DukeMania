package it.dukemania.Controller.logic;

public interface LogicNote {

    /**
     * 
     * @return the hight of the note
     */
    int getHeight();

    /**
     * 
     * @return the column of the note
     */
    Columns getColumn();

    /**
     * 
     * @return the start time of the AbstractNote in microseconds
     */
    long getNoteStarts();

    /**
     * 
     * @return the duration of the AbstractNote in microseconds
     */
    Long getNoteDuration();

}

