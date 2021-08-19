package it.dukemania.midi;

import java.util.List;
import java.util.Optional;

public class PercussionFactoryImpl implements AbstractFactory {
    private static final int MIN = 35;
    private static final int MAX = 81;

    @Override
    public final AbstractNote createNote(final Optional<Long> duration, final long startTime, final int identifier) {
        if (identifier >= MIN && identifier <= MAX) {
            return new PercussionNote(duration, startTime, identifier);
        } else {
            throw new InvalidNoteException();
            //return null;
        }
    }

    @Override
    public final MidiTrack createTrack(final Enum<InstrumentType> instrument, final List<AbstractNote> notes,
            final int channel) {
        return new PercussionTrackImpl(notes, channel);
    }

}

