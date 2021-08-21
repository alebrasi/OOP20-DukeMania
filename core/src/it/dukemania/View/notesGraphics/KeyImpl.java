package it.dukemania.View.notesGraphics;

import java.time.Instant;

import it.dukemania.Controller.logic.Columns;



public class KeyImpl implements Key {
    private long initialTime;
    private long finalTime;
    private final Columns column;
    //private EventsFromKeyboard keyboard;
    private final Note note;

    public KeyImpl(final Note note) {
        this.initialTime = 0;
        this.finalTime = 0;
        this.note = note;
        this.column = this.note.getColumn();
        //this.keyboard = new EventsFromKeyboardImpl(note);
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
        return this.finalTime - this.note.getTimeOfFall();
    }

    @Override
    public long getInitialTime() {
        return this.initialTime - this.note.getTimeOfFall();
    }

    @Override
    public Columns getColumn() {
        return this.column;
    }

}
