package it.dukemania.logic;

import java.util.Collection;
import it.dukemania.midi.MyTrack;
import it.dukemania.midi.Song;



public interface TrackFilter {

    Collection<MyTrack> reduceTrack(Song song);

}
