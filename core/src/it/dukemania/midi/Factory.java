package it.dukemania.midi;

import java.util.List;
import java.util.Optional;

public class Factory implements AbstractFactory {

    @Override
    public final Note createNote(final Optional<Double> duration, final int startTime, final int identifier) {
        return new Note(duration, startTime, identifier);
    }

    @Override
    public final TrackImpl createTrack(final Enum<InstrumentType> instrument, final List<AbstractNote> notes, final int channel) {
        return new TrackImpl(instrument, notes, channel);
    }

}
