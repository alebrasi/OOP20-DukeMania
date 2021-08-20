package it.dukemania.Controller.logic;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import it.dukemania.midi.MidiTrack;



public class GameUtilitiesImpl implements GameUtilities {

    private List<DifficultyLevel> getDifficulties() {
        List<DifficultyLevel> difficulties = Arrays.stream(DifficultyLevel.values())
                .collect(Collectors.toList());
        difficulties.sort((e1, e2) -> e1.getNumericValue().compareTo(e2.getNumericValue()));
        return difficulties;
    }

    @Override
    public final Map<MidiTrack, DifficultyLevel> generateTracksDifficulty(final List<MidiTrack> tracks) {
        return tracks.stream()
                .collect(Collectors
                        .toMap(x -> x, x -> { 
                            int numberOfDifficulties = DifficultyLevel.values().length - 1;
                            Optional<DifficultyLevel> difficulty = getDifficulties().stream()
                                    .filter(y -> 
                                    x.getNotes().size() <= TrackFilterImpl.MAX_NOTE / numberOfDifficulties * y.getNumericValue())
                                    .findFirst();
                            return difficulty.orElse(DifficultyLevel.UNKNOWN);
                        }));
    }

}
