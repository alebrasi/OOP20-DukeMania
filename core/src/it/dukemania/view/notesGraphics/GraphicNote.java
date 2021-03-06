package it.dukemania.view.notesGraphics;

import java.util.Optional;

import it.dukemania.controller.logic.Columns;


public interface GraphicNote {
    /***
     * update the note position.
     * @param deltaTime
     * @param startTime
     */
    void updateNote(float deltaTime, long startTime);
    /***
     * 
     * @return the start time of the note
     */
    long getStartTime();

    /***
     * set the start time of the note.
     * @param time
     */
    void setStartNote(long time);

    /***
     * 
     * @return the duration of the note
     */
    long getDuration();

    /***
     * 
     * @return the fall time of the note
     */
    long getTimeOfFall();

    /***
     * 
     * @return the column of the note
     */
    Columns getColumn();

    /***
     * 
     * @return the vertical position of the note
     */
    int getPosyNote();

    /***
     * 
     * @return the horizontal position of the note
     */
    int getPosxNote();

    /***
     * 
     * @return the height of the note
     */
    int getyNote();

    /***
     * 
     * @return the width of the note
     */
    int getxNote();

    /***
     * 
     * @return the horizontal position of the spark
     */
    int getPosxSpark();

    /***
     * 
     * @return the width of the spark
     */
    int getxSpark();

    /***
     * 
     * @return the height of the spark
     */
    int getySpark();


    /***
     * 
     * @return true if a button is pressed
     */
    boolean isPressed();

    /***
     * Set the variable isPressed using the value of the input parameter.
     * @param isPressed
     */
    void setIsPressed(boolean isPressed);

    /***
     * 
     * @return the keyboard associated to the note
     */
    Optional<EventsFromKeyboard> getKeyboard();

    /***
     * Associate a keyboard to the note.
     */
    void setKeyboard();

    /***
     * 
     * @return the key associated to the note
     */
    Optional<Key> getKey();

    /***
     * Associate a key to the note.
     */
    void setKey();

}
