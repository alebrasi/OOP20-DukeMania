package it.dukemania.View.notesGraphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public interface Note {
	
	void updateNote(float deltaTime);
	
	long getStartTime();
	
	long getDuration();
	
	long getTimeOfFall();
	
	Columns getColumn();
	
	int getPosyNote();
	
	int getPosxNote();
	
	int getPosxSpark();
	
	int getxSpark();
	
	int getySpark();
	
	boolean isPressed();
	
	void setIsPressed(boolean isPressed);
	
}
