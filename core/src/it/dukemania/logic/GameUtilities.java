package it.dukemania.logic;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.dukemania.midi.MyTrack;



public interface GameUtilities {

    Map<MyTrack, DifficultyLevel> generateTracksDifficulty(List<MyTrack> tracks);

}
