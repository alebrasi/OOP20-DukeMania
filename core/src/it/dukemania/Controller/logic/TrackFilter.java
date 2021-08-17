package it.dukemania.Controller.logic;

import java.util.List;

import it.dukemania.midi.MidiTrack;
import it.dukemania.midi.Song;



public interface TrackFilter {

    List<MidiTrack> reduceTrack(Song song);

}
