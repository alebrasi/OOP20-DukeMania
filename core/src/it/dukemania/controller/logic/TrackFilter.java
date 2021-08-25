package it.dukemania.controller.logic;

import java.util.List;

import it.dukemania.midi.ParsedTrack;
import it.dukemania.midi.Song;

public interface TrackFilter {

    /**
     * 
     * @param song the song to filter
     * @return the filtered tracks of the song
     */
    List<ParsedTrack> reduceTrack(Song song);

}
