package it.dukemania.Controller.logic;

import java.util.List;
import it.dukemania.midi.MidiTrack;
import it.dukemania.midi.Song;


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

    /**
     * 
     * Add a new NoteRange for score purposes.
     * @param column range column
     * @param start range start time in microseconds
     * @param end range end time in microseconds
     */
    void addNoteRanges(Columns column, long start, long end);

    /**
     * Initialize the audio player.
     * @param song the song configuration
     */
    void initAudio(Song song);

    /**
     * play a buffer from the audio Player.
     */
    void play();

    /**
     * 
     * @param track the track to play
     * @return a list of LogicNote distributed in the columns
     */
    List<LogicNote> noteQueuing(MidiTrack track);

    /**
     * 
     * @param column the column of the note pressed
     * @param start the start time of the note pressed in microseconds 
     * @param end the end time of the note pressed in microseconds
     * @return a score for the note pressed
     */
    int verifyNote(Columns column, long start, long end);



}
