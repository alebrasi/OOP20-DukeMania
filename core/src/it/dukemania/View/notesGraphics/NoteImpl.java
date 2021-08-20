package it.dukemania.View.notesGraphics;

import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;


public class NoteImpl implements Note {
    private int posxNote; //note's position
    private int posyNote;
    private int xNote;
    private int yNote; //texture's dimension
    private Double speedNote;
    private Columns column;
    private int posyBlue;
    private static final int FINISH_LINE = 108;
    private int posxSparks;
    private int xSparks;
    private int ySparks;
    private long startNote;
    private long duration;
    private Size dimensions = new SizeImpl();
    private ComputingShift shift = new ComputingShiftImpl();
    private long timeOfFall;
    private boolean isPressed = false;
    //private ShapeRenderer renderer = new ShapeRenderer();
	
	
	public NoteImpl(final int heightpos, final Columns letter, final int posyBlue, final int height, final long startNote, final long duration, final int numberOfColumns) {
		this.xNote = 50;
		this.yNote = height;
		this.column = letter;
		this.posxNote = (letter.getNumericvalue() - 1) * this.dimensions.getSize().getX() / numberOfColumns 
		        + this.shift.calculateShifting(numberOfColumns) * letter.getNumericvalue() + this.shift.getNoteShift();
		this.posyNote = heightpos;
		this.speedNote = 200.0;
		this.posyBlue = posyBlue;
		this.posxSparks = this.posxNote - 25;
		this.xSparks = 100;
		this.ySparks = 70;
		this.startNote = startNote;
		this.duration = duration;
		this.timeOfFall = 0;
	}

	@Override
	public void updateNote(final float deltaTime) {
	    this.posyNote -= this.speedNote * deltaTime;

	    if (this.posyNote == this.FINISH_LINE) {
	        this.timeOfFall = Instant.now().toEpochMilli() - this.startNote;
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
        return this.xNote;
    }






	


}
