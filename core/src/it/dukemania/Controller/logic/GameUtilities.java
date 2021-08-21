package it.dukemania.Controller.logic;

import java.util.List;
import java.util.Map;

import it.dukemania.midi.MidiTrack;



public interface GameUtilities {

    /**
     * 
     * @param tracks a list of track 
     * @return a map of track and its DifficultyLevel
     */
    Map<MidiTrack, DifficultyLevel> generateTracksDifficulty(List<MidiTrack> tracks);

}
