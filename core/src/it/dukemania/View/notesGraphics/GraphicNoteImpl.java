package it.dukemania.View.notesGraphics;

import java.time.Instant;
import java.util.Optional;
import it.dukemania.Controller.logic.Columns;


public class GraphicNoteImpl implements GraphicNote {
    private final int posxNote; //note's position
    private int posyNote;
    private static final int XNOTE = 50;
    private final int yNote; //texture's dimension
    private static final Double NOTE_SPEED = 200.0;
    private final Columns column;
    private static final int FINISH_LINE = 108;
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

	
	
	public GraphicNoteImpl(final int heightpos, final Columns letter, final int height, final long startNote, final long duration, final int numberOfColumns) {
		this.yNote = height;
		this.column = letter;
	    final Size dimensions = new SizeImpl(numberOfColumns);
		this.posxNote = (letter.getNumericValue() - 1) * dimensions.getSize().getX() / numberOfColumns 
		        + this.shift.calculateShifting(numberOfColumns) * letter.getNumericValue() + this.shift.getNoteShift();
		this.posyNote = heightpos;
		this.posxSparks = this.posxNote - this.shift.getHorizontalSparkShift();
		this.xSparks = 100;
		this.ySparks = this.shift.getSparksHeight();
		this.startNote = startNote;
		this.duration = duration;
		this.timeOfFall = 0;
	}

	@Override
	public void updateNote(final float deltaTime, final long startTime) {
	    this.posyNote -= GraphicNoteImpl.NOTE_SPEED * deltaTime;
	    if (this.posyNote <= GraphicNoteImpl.FINISH_LINE + GraphicNoteImpl.DISPLACEMENT  && this.posyNote >= GraphicNoteImpl.FINISH_LINE - GraphicNoteImpl.DISPLACEMENT) {
	        this.timeOfFall = Instant.now().toEpochMilli() - startNoteprova;
	    }
	}

	@Override
	public long getStartTime() {
		return this.startNote;
	}
	
	@Override
	public long getDuration() {
	    return this.duration;
	}

	@Override
	public long getTimeOfFall() {
	    return this.timeOfFall;
	}

	@Override
	public Columns getColumn() {
		return this.column;
	}

	@Override
	public int getPosyNote() {
		return this.posyNote;
	}
	
	@Override
	public int getPosxNote() {
	    return this.posxNote;
	}

    @Override
    public boolean isPressed() {
        return this.isPressed;
    }

    @Override
    public void setIsPressed(final boolean isPressed) {
        this.isPressed = isPressed;
    }

    @Override
    public int getPosxSpark() {
        return this.posxSparks;
    }

    @Override
    public int getxSpark() {
        return this.xSparks;
    }

    @Override
    public int getySpark() {
        return this.ySparks;
    }

    @Override
    public int getyNote() {
        return this.yNote;
    }

    @Override
    public int getxNote() {
        return GraphicNoteImpl.XNOTE;
    }

    @Override
    public void setKeyboard() {
        this.keyboard = Optional.of(new EventsFromKeyboardImpl(this.column));
    }

    @Override
    public Optional<EventsFromKeyboard> getKeyboard() {
        return this.keyboard;
    }

    @Override
    public Optional<Key> getKey() {
        return this.key;
    }

    @Override
    public void setKey() {
        this.key = Optional.of(new KeyImpl(this.column));
    }

    @Override
    public void setStartNote(final long time) {
        this.startNoteprova = time;
    }

}
