package it.dukemania.logic;

import java.util.Collection;
import java.util.Map;

import it.dukemania.midi.MyTrack;

public interface GraphicUtilities {
    Map<MyTrack, DifficultyLevel> setDifficulty(Collection<MyTrack> tracks);
}
