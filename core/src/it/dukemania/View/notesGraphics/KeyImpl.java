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
    public void startPressing(long startTime) {
        this.initialTime = Instant.now().toEpochMilli() - startTime;
        System.out.println("startTime " + startTime + "initialTime" + initialTime);
        //System.out.println("inizio a premere il tasto" + this.initialTime);
    }

    @Override
    public void finishPressing(long startTime) {
        this.finalTime = Instant.now().toEpochMilli() - startTime;
        System.out.println("startTime " + startTime + "finalTime" + finalTime);
        //System.out.println("finisco di premere il tasto" + this.finalTime);
    }

    @Override
    public long getFinalTime() {
        return this.finalTime ;
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
