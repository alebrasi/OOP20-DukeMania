package it.dukemania.midi;

import java.util.List;
import java.util.Optional;

public class PercussionFactory implements AbstractFactory {
    private static final int MIN = 35;
    private static final int MAX = 81;

    @Override
    public final PercussionNote createNote(final Optional<Double> duration, final int startTime, final int identifier) {
        if (identifier >= MIN && identifier <= MAX) {
            return new PercussionNote(duration, startTime, identifier);
        } else {
            throw new InvalidNoteException();
            //return null;
        }
    }

    @Override
    public final PercussionTrack createTrack(final Enum<InstrumentType> instrument, final List<AbstractNote> notes, final int channel) {
        return new PercussionTrack(notes, channel);
    }

}

