package it.dukemania.View.notesGraphics;

import java.util.Optional;

public interface EventsFromKeyboard {

    boolean isColumnSelected(int max);

    boolean isButtonPressed(int numberOfColumn, int max);

    int associationKeyColumn(int numberOfColumn, int max);


}

