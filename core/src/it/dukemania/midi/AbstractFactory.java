package it.dukemania.midi;

import java.util.List;
import java.util.Optional;

public interface AbstractFactory {

    AbstractNote createNote(Optional<Double> duration, int startTime, int identifier);

    TrackInterface createTrack(Enum<InstrumentType> instrument, List<AbstractNote> notes, int channel);

}
