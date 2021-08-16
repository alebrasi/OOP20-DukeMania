package it.dukemania.View.notesGraphics;



public interface Key {

    void startPressing();

    void finishPressing();

    long getFinalTime();

    long getInitialTime();

    Columns getColumn();
}
