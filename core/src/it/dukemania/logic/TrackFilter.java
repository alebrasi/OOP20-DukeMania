package it.dukemania.logic;

import java.util.List;

import it.dukemania.midi.MyTrack;
import it.dukemania.midi.Song;



public interface TrackFilter {

    int MAX_NOTE = 600;

    List<MyTrack> reduceTrack(Song song);

}
