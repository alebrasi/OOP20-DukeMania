package it.dukemania.View.notesGraphics;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import it.dukemania.Controller.logic.Columns;


public class GraphicNoteImpl implements GraphicNote {
    private final int posxNote; //note's position
    private int posyNote;
    private static final int XNOTE = 50;
    private final int yNote; //texture's dimension
    private static final Double NOTE_SPEED = 200.0;
    private final Columns column;
    private final int finishLine;
    private static final int DISPLACEMENT = 5;
    private final int posxSparks;
    private final int xSparks;
    private final int ySparks;
    private final long startNote;
    private long startNoteprova;
    private final long duration;
    private final ComputingShift shift = new ComputingShiftImpl();
    private long timeOfFall;
    private boolean isPressed;
    private Optional<EventsFromKeyboard> keyboard = Optional.empty(); 
    private Optional<Key> key = Optional.empty();



    public GraphicNoteImpl(final int heightpos, final Columns letter, final int height, final long startNote,
            final long duration, final int yButton, final List<Integer> posXButtons) {
        this.yNote = height;
        this.column = letter;
        this.posxNote = posXButtons.get(letter.getNumericValue() - 1) + yButton / 2 - GraphicNoteImpl.XNOTE / 2;
        this.posyNote = heightpos;
        this.posxSparks = this.posxNote - this.shift.getHorizontalSparkShift();
        this.xSparks = 100;
        this.ySparks = this.shift.getSparksHeight();
        this.startNote = startNote;
        this.duration = duration;
        this.timeOfFall = 0;
        this.finishLine = yButton;
    }

    @Override
    public final void updateNote(final float deltaTime, final long startTime) {
        this.posyNote -= GraphicNoteImpl.NOTE_SPEED * deltaTime;
        if (this.posyNote <= this.finishLine + GraphicNoteImpl.DISPLACEMENT 
                && this.posyNote >= this.finishLine - GraphicNoteImpl.DISPLACEMENT) {
            this.timeOfFall = Instant.now().toEpochMilli() - startNoteprova;
        }
    }

    @Override
    public final long getStartTime() {
        return this.startNote;
    }

    @Override
    public final long getDuration() {
        return this.duration;
    }

    @Override
    public final long getTimeOfFall() {
        return this.timeOfFall;
    }

    @Override
    public final Columns getColumn() {
        return this.column;
    }

    @Override
    public final int getPosyNote() {
        return this.posyNote;
    }

    @Override
    public final int getPosxNote() {
        return this.posxNote;
    }

    @Override
    public final boolean isPressed() {
        return this.isPressed;
    }

    @Override
    public final void setIsPressed(final boolean isPressed) {
        this.isPressed = isPressed;
    }

    @Override
    public final int getPosxSpark() {
        return this.posxSparks;
    }

    @Override
    public final int getxSpark() {
        return this.xSparks;
    }

    @Override
    public final int getySpark() {
        return this.ySparks;
    }

    @Override
    public final int getyNote() {
        return this.yNote;
    }

    @Override
    public final int getxNote() {
        return GraphicNoteImpl.XNOTE;
    }

    @Override
    public final void setKeyboard() {
        this.keyboard = Optional.of(new EventsFromKeyboardImpl(this.column));
    }

    @Override
    public final Optional<EventsFromKeyboard> getKeyboard() {
        return this.keyboard;
    }

    @Override
    public final Optional<Key> getKey() {
        return this.key;
    }

    @Override
    public final void setKey() {
        this.key = Optional.of(new KeyImpl(this.column));
    }

    @Override
    public final void setStartNote(final long time) {
        this.startNoteprova = time;
    }

}
