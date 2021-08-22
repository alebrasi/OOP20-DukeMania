package it.dukemania.View.notesGraphics;

import java.time.Instant;

import it.dukemania.Controller.logic.Columns;



public class KeyImpl implements Key {
    private long initialTime;
    private long finalTime;
    private final long timeOfFall;
    private final Columns column;



    public KeyImpl(final Columns column, final long timeOfFall) {
        this.initialTime = 0;
        this.finalTime = 0;
        this.timeOfFall = timeOfFall;
        this.column = column;

    }


    @Override
    public void startPressing() {
        this.initialTime = Instant.now().toEpochMilli();
        System.out.println("inizio a premere il tasto" + this.initialTime);
    }

    @Override
    public void finishPressing() {
        this.finalTime = Instant.now().toEpochMilli();
        System.out.println("finisco di premere il tasto" + this.finalTime);
    }

    @Override
    public long getFinalTime() {
        return this.finalTime - this.timeOfFall;
    }

    @Override
    public long getInitialTime() {
        return this.initialTime - this.timeOfFall;
    }

    @Override
    public Columns getColumn() {
        return this.column;
    }

}
