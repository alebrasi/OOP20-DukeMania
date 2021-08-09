package it.dukemania.View.notesGraphics;

import it.dukemania.View.notesGraphics.ColumnsEnum.Columns;


public interface Key {

    void startPressing();

    void finishPressing();

    long getFinalTime();

    long getInitialTime();

    Columns getColumn();
}
