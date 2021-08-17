package it.dukemania.Controller.logic;

import java.util.List;
import java.util.Map;

import it.dukemania.midi.MidiTrack;



public interface GameUtilities {

    Map<MidiTrack, DifficultyLevel> generateTracksDifficulty(List<MidiTrack> testTracksDiff);

}
