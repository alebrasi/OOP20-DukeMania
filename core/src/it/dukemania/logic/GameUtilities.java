package it.dukemania.logic;

import java.util.List;
import java.util.Map;

import it.dukemania.midi.MyTrack;



public interface GameUtilities {

    Map<MyTrack, DifficultyLevel> setTracksDifficulty(List<MyTrack> tracks);
}
