package it.dukemania.View.notesGraphics;

import it.dukemania.Controller.logic.Columns;

public interface Key {

    /***
     * set the time when you start to press a button.
     */
    void startPressing(long startTime);

    /***
     * set the time when you finish to press a button.
     */
    void finishPressing(long startTime);

    /***
     * 
     * @return the time when you finish to press a button
     */
    long getFinalTime();

    /***
     * 
     * @return the time when you start to press a button
     */
    long getInitialTime();

    /***
     * 
     * @return the column of the given note
     */
    Columns getColumn();
}
