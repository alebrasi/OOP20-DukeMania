package it.dukemania.view.notesGraphics;

import it.dukemania.util.Pair;

public interface Size {

    /***
     * 
     * @return the size of the game GUI
     */
    Pair<Integer, Integer> getSize();

    /***
     * 
     * @return the numbers of the columns
     */
    int getNumberOfColumns();
}
