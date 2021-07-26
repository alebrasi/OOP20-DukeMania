package it.dukemania.View.notesGraphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.dukemania.View.notesGraphics.ColumnsEnum.Columns;

public interface Note {
	
	void drawNote();
	
	void isSparked(Columns type, SpriteBatch batch);
	
	long getTime();
	
	Columns getColumn();
	
	int getPosyNote();
	
}
