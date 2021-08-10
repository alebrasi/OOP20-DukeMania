package it.dukemania.logic;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.dukemania.midi.MyTrack;

public interface GameUtilities {
    
    Map<MyTrack, DifficultyLevel> setTracksDifficulty(Collection<MyTrack> tracks);
}
