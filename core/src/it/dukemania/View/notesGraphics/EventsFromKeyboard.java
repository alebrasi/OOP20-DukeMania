package it.dukemania.View.notesGraphics;

import it.dukemania.View.notesGraphics.ColumnsEnum.Columns;

public interface EventsFromKeyboard {

    boolean isColumnSelected();

    boolean isButton1Pressed();

    boolean isButton2Pressed();

    boolean isButton3Pressed();

    boolean isButton4Pressed();

    int associationKeyColumn(Columns column);

}

