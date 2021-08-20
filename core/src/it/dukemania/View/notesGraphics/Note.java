package it.dukemania.View.notesGraphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.dukemania.Controller.logic.Columns;


public interface Note {
	
    /***
     * 
     * @param deltaTime
     */
	void updateNote(float deltaTime);
	
	long getStartTime();
	
	long getDuration();
	
	long getTimeOfFall();
	
	Columns getColumn();
	
	int getPosyNote();
	
	int getPosxNote();
	
	int getyNote();
	
	int getxNote();
	
	int getPosxSpark();
	
	int getxSpark();
	
	int getySpark();
	
	boolean isPressed();
	
	void setIsPressed(boolean isPressed);
	
}
