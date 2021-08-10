package it.dukemania.logic;

import java.util.Collection;
import java.util.Map;

import it.dukemania.midi.MyTrack;

public interface GameUtilities {
    
    Map<MyTrack, DifficultyLevel> setDifficulty(Collection<MyTrack> tracks);
}
