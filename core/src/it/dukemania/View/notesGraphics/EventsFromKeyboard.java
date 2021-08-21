package it.dukemania.View.notesGraphics;



public interface EventsFromKeyboard {

    /***
     * 
     * @param max
     * @return true if the column of the note is selected pressing the key
     */
    boolean isColumnSelected(int max);

    /***
     * 
     * @param numberOfColumn
     * @param max
     * @return true if the button related to the specific column numberOfColumn is pressed
     */
    boolean isButtonPressed(int numberOfColumn, int max);

    /***
     * 
     * @param numberOfColumn
     * @param max
     * @return the key associated to the given column
     */
    int associationKeyColumn(int numberOfColumn, int max);


}

