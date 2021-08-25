package it.dukemania.view.notesGraphics;

import it.dukemania.controller.logic.Columns;

public interface Key {

    /***
     * set the time when you start to press a button.
     * @param startTime
     */
    void startPressing(long startTime);

    /***
     * set the time when you finish to press a button.
     * @param startTime
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
