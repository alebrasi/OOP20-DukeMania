package it.dukemania.Controller.logic;

import java.util.List;

import it.dukemania.midi.MidiTrack;
import it.dukemania.midi.Song;

public interface TrackFilter {

    /**
     * 
     * @param song the song to filter
     * @return the filtered tracks of the song
     */
    List<MidiTrack> reduceTrack(Song song);

}
