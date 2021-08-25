package it.dukemania.view.notesGraphics;



public interface EventsFromKeyboard {

    /***
     * 
     * @param numberOfColumn
     * @return true if the button related to the note is pressed
     */
    boolean isButtonPressed(int numberOfColumn);



    /***
     * 
     * @param numberOfColumn
     * @param max
     * @return the key associated to the given column
     */
    int associationKeyColumn(int numberOfColumn, int max);


}

