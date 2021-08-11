package it.dukemania.logic;

import java.util.Collection;
import it.dukemania.midi.MyTrack;
import it.dukemania.midi.Song;



public interface TrackFilter {

    int MAX_NOTE = 600;

    Collection<MyTrack> reduceTrack(Song song);

}
