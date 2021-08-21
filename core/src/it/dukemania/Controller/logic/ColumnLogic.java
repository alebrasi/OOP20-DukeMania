package it.dukemania.Controller.logic;

import java.util.List;
import it.dukemania.midi.MidiTrack;


public interface ColumnLogic {

    /**
     * 
     * @return the number of column selected
     */
    int getColumnNumber();

    /**
     * 
     * @param columnNumber new columnNumber
     * only available values between COLUMN_MAX_CAP and COLUMN_MIN_CAP
     */
    void setColumnNumber(int columnNumber);

    void addNoteRanges(Columns column, long start, long end);

    /**
     * 
     * @param track the track to play
     * @return a list of LogicNote distributed in the columns
     */
    List<LogicNote> noteQueuing(MidiTrack track);

    /**
     * 
     * @param column the column of the note pressed
     * @param start the start time of the note pressed
     * @param end the end time of the note pressed
     * @return a score for the note pressed
     */
    int verifyNote(Columns column, long start, long end);



}
