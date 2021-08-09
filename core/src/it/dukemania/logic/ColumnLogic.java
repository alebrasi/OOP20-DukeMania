package it.dukemania.logic;

import java.util.List;

import it.dukemania.midi.MyTrack;
import it.dukemania.midi.Note;


public interface ColumnLogic {

    int getColumnNumber();

    void increaseColumnNumber();

    void decreaseColumnNumber();

    List<List<Note>> noteQueuing(MyTrack track);

    //riceve come imput i dati dalla grafica, verifica se l'intervallo in cui � stato premuto il tasto
    //coincide con quello della nota e restituisce un punteggio influenzato da:
    //precisione, tolleranza, combo e numero colonne
    int verifyNote(ColumnsEnum column, int start, int end);
}
