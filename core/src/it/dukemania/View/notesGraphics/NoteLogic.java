package it.dukemania.View.notesGraphics;

import it.dukemania.View.notesGraphics.ColumnsEnum.Columns;

public interface NoteLogic {
	
	Columns getColumn();
	
	long getTimeStart();
	
	int getHeight();
	
	long getDuration();
}
