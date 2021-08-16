package it.dukemania.midi;

import java.util.List;
import java.util.Optional;

public interface AbstractFactory {

    AbstractNote createNote(Optional<Long> duration, long startTime, int identifier);

    MidiTrack createTrack(Enum<InstrumentType> instrument, List<AbstractNote> notes, int channel);

}
