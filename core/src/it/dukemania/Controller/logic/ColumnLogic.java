package it.dukemania.Controller.logic;

import java.util.List;
import it.dukemania.midi.MidiTrack;


public interface ColumnLogic {

    int getColumnNumber();

    void setColumnNumber(int columnNumber);

    List<LogicNote> noteQueuing(MidiTrack track);

    int verifyNote(Columns column, long start, long end);
}
