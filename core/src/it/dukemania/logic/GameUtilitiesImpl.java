package it.dukemania.logic;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import it.dukemania.midi.MyTrack;



public class GameUtilitiesImpl implements GameUtilities {

    static final int MAX_HEIGHT = 4;
/*   
    private static <A,B> Map<A, B> associateValueToElements(final List<A> elements, List<B> values, int numberOfValues, int maxRange, B correctionValue) {
        return elements.stream()
                .collect(Collectors
                        .toMap(x -> x, x -> { 
                            //int numberOfDifficulties = DifficultyLevel.values().length - 1;
                            Optional<B> value = values.stream()
                                    .filter(y -> 
                                    operation1(x) <= maxRange / numberOfValues * operation2(y))
                                    .findFirst();
                            return value.isEmpty() ? correctionValue : value.get();
                        }));
    }
*/
    private List<DifficultyLevel> getDifficulties() {
        List<DifficultyLevel> difficulties = Arrays.stream(DifficultyLevel.values())
                .collect(Collectors.toList());
        difficulties.sort((e1, e2) -> e1.getNumericValue().compareTo(e2.getNumericValue()));
        return difficulties;
    }

    @Override
    public final Map<MyTrack, DifficultyLevel> setTracksDifficulty(final List<MyTrack> tracks) {
        //return associateValueToElements(tracks, getDifficulties(), DifficultyLevel.values().length - 1, TrackFilterImpl.MAX_NOTE, DifficultyLevel.UNKNOWN);
        return tracks.stream()
                .collect(Collectors
                        .toMap(x -> x, x -> { 
                            int numberOfDifficulties = DifficultyLevel.values().length - 1;
                            Optional<DifficultyLevel> difficulty = getDifficulties().stream()
                                    .filter(y -> 
                                    x.getNotes().size() <= TrackFilterImpl.MAX_NOTE / numberOfDifficulties * y.getNumericValue())
                                    .findFirst();
                            return difficulty.isEmpty() ? DifficultyLevel.UNKNOWN : difficulty.get();
                        }));
    }
    //return an int between 1 and 4 based on the duration of the note and the max duration of a note in the current track
    public static final int setNoteHeight(final Optional<Double> noteDuration, final Optional<Double> maxDuration) {
        return IntStream.iterate(1, i -> i + 1)
        .limit(MAX_HEIGHT)
        .filter(x -> 
        (noteDuration.isEmpty() ? 0 : Math.round(noteDuration.get()))
        <= (maxDuration.isEmpty() ? 0 : Math.round(maxDuration.get())) / MAX_HEIGHT * x )
        .findFirst()
        .getAsInt();


    }
}
