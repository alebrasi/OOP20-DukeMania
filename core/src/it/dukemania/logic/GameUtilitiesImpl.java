package it.dukemania.logic;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import it.dukemania.midi.MyTrack;


public class GameUtilitiesImpl implements GameUtilities {

    private List<DifficultyLevel> getDifficulties() {
        List<DifficultyLevel> difficulties = Arrays.stream(DifficultyLevel.values())
                .collect(Collectors.toList());
        difficulties.sort((e1, e2) -> e1.getNumericValue().compareTo(e2.getNumericValue()));
        return difficulties;
    }

    @Override
    public final Map<MyTrack, DifficultyLevel> setTracksDifficulty(final Collection<MyTrack> tracks) {
        return tracks.stream()
                .collect(Collectors
                        .toMap(x -> x, x -> { 
                            int numberOfDifficulties = DifficultyLevel.values().length - 1;
                            Optional<DifficultyLevel> difficulty = getDifficulties().stream()
                                    .filter(y -> 
                                    x.getNotes().size() <= TrackFilterImpl.MAX_NOTE / numberOfDifficulties * y.getNumericValue())
                                    .findFirst();
                            return difficulty.isEmpty() ? DifficultyLevel.SCONOSCIUTO : difficulty.get();
                        }));
    }
}
