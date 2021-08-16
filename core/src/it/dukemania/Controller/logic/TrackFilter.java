package it.dukemania.Controller.logic;

import java.util.List;

import it.dukemania.midi.MyTrack;
import it.dukemania.midi.Song;



public interface TrackFilter {

    List<MyTrack> reduceTrack(Song song);

}
