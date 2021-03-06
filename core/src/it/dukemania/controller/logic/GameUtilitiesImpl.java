package it.dukemania.controller.logic;

import java.util.Optional;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
                            final int numberOfDifficulties = DifficultyLevel.values().length - 1;
                            Optional<DifficultyLevel> difficulty = getDifficulties().stream()
                                    .filter(y -> 
                                    x.getNotes().size() <= TrackFilterImpl.MAX_NOTE / numberOfDifficulties * y.getNumericValue())
                                    .findFirst();
                            return difficulty.orElse(DifficultyLevel.UNKNOWN); //debug value
                        }));
    }

}
