package it.dukemania.View.notesGraphics;




import java.time.Instant;


public class KeyImpl implements Key {
    private long time;
    private long initialTime;
    private long finalTime;
    private Columns column;
    private EventsFromKeyboard keyboard;
    private Note note;

    public KeyImpl(final Note note, final long actualTime) {
        this.initialTime = 0;
        this.finalTime = 0;
        this.time = actualTime;
        this.note = note;
        this.column = this.note.getColumn();
        this.keyboard = new FromEventToInput(note);
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
