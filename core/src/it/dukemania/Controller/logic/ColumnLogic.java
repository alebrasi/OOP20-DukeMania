package it.dukemania.Controller.logic;

import java.util.List;
import it.dukemania.Model.MyTrack;
import it.dukemania.midi.MidiTrack;


public interface ColumnLogic {

    int getColumnNumber();

    void setColumnNumber(int columnNumber);

    List<List<LogicNoteImpl>> noteQueuing(MidiTrack track);

    //riceve come imput i dati dalla grafica, verifica se l'intervallo in cui � stato premuto il tasto
    //coincide con quello della nota e restituisce un punteggio influenzato da:
    //precisione, tolleranza, combo e numero colonne
    int verifyNote(Columns column, long start, long end);
}
