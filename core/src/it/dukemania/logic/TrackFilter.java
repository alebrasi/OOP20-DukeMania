package it.dukemania.logic;

import java.util.Collection;
import java.util.Map;

import it.dukemania.midi.MyTrack;
import it.dukemania.midi.Song;




public interface TrackFilter {

    Collection<MyTrack> selectTrack(Song song);

    Collection<MyTrack> reduceTrack(Song song);

    Map<MyTrack, DifficultyLevel> setDifficulty(Collection<MyTrack> tracks);

}
