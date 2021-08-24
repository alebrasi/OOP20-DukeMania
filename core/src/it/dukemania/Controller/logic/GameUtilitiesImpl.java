package it.dukemania.Controller.logic;

import java.util.*;
import java.util.stream.Collectors;
import it.dukemania.midi.ParsedTrack;



public class GameUtilitiesImpl implements GameUtilities {

    private List<DifficultyLevel> getDifficulties() {
        List<DifficultyLevel> difficulties = Arrays.stream(DifficultyLevel.values())
                .collect(Collectors.toList());
        difficulties.sort(Comparator.comparing(DifficultyLevel::getNumericValue));
        return difficulties;
    }

    @Override
    public final Map<ParsedTrack, DifficultyLevel> generateTracksDifficulty(final List<ParsedTrack> tracks) {
        return tracks.stream()
                .collect(Collectors
                        .toMap(x -> x, x -> { 
                            int numberOfDifficulties = DifficultyLevel.values().length - 1;
                            Optional<DifficultyLevel> difficulty = getDifficulties().stream()
                                    .filter(y -> 
                                    x.getNotes().size() <= TrackFilterImpl.MAX_NOTE / numberOfDifficulties * y.getNumericValue())
                                    .findFirst();
                            return difficulty.orElse(DifficultyLevel.UNKNOWN); //debug value
                        }));
    }

}
