package it.dukemania.View.notesGraphics;

import it.dukemania.Controller.logic.Columns;

public interface Key {

    void startPressing();

    void finishPressing();

    long getFinalTime();

    long getInitialTime();

    Columns getColumn();
}
