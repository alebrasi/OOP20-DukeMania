package it.dukemania.View.notesGraphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.dukemania.View.notesGraphics.ColumnsEnum.Columns;

public interface Note {
	
	void drawNote();
	
	void isSparked(Columns type, SpriteBatch batch);
	
	long getStartTime();
	
	long getDuration();
	
	long getTimeOfFall();
	
	Columns getColumn();
	
	int getPosyNote();
	
	boolean isPressed();
	
	void setIsPressed(boolean isPressed);
	
}
