package it.dukemania.View.notesGraphics;




import java.time.Instant;


public class KeyImpl implements Key {
    private long time;
    private long initialTime;
    private long finalTime;
    private Columns column;
    private EventsFromKeyboard keyboard;
    private Note note;
    //private boolean isPressed = false;

    //inizia a 3 finisce 
    //primo pixel arriva sul bottone => tempo caduta
    //ide, altri pixel

    public KeyImpl(final Note note, final long actualTime) {
        this.initialTime = 0;
        this.finalTime = 0;
        this.time = actualTime;
        this.note = note;
        this.column = this.note.getColumn();
        this.keyboard = new FromEventToInput(note, 4); //replace 4 with the number of columns chosen by the user
    }

    /*nota( start = 0, durata = 10)
    5 = tempo caduta
    15 = istante fine premuto
    colonna1 , 5, 15
    0 10*/
    //vuole 0 e 10

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




  //se non � premuto e lo premi hai iniziato a premerlo in quel momentoi //tempo inizio tasto premuto
    //se il tasto erapremuto e continua ad esserlo lo stai tenendo giu
    //se il tasto era premuto e ora non lo � pi� prendo il tempo di fine
}
