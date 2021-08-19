package it.dukemania.midi;

import java.util.List;
import java.util.Optional;

public class FactoryImpl implements AbstractFactory {

    @Override
    public final AbstractNote createNote(final Optional<Long> duration, final long startTime, final int identifier) {
        return new Note(duration, startTime, identifier);
    }

    @Override
    public final MidiTrack createTrack(final Enum<InstrumentType> instrument, final List<AbstractNote> notes, final int channel) {
        return new TrackImpl(instrument, notes, channel);
    }

}
