package it.dukemania.View.notesGraphics;


public interface EventsFromKeyboard {

    boolean isColumnSelected();

    boolean isButton1Pressed();

    boolean isButton2Pressed();

    boolean isButton3Pressed();

    boolean isButton4Pressed();

    boolean isButtonPressed(int numberOfColumn);

    int associationKeyNumber(int numberOfColumn);

}

