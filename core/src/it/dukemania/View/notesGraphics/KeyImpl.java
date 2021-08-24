package it.dukemania.View.notesGraphics;

import java.time.Instant;

import it.dukemania.Controller.logic.Columns;



public class KeyImpl implements Key {
    private long initialTime;
    private long finalTime;
    private final Columns column;



    public KeyImpl(final Columns column) {
        this.initialTime = 0;
        this.finalTime = 0;
        this.column = column;
    }


    @Override
    public void startPressing(final long startTime) {
        this.initialTime = Instant.now().toEpochMilli() - startTime;
    }

    @Override
    public void finishPressing(final long startTime) {
        this.finalTime = Instant.now().toEpochMilli() - startTime;
    }

    @Override
    public long getFinalTime() {
        return this.finalTime;
    }

    @Override
    public long getInitialTime() {
        return this.initialTime;
    }

    @Override
    public Columns getColumn() {
        return this.column;
    }

}
