package it.dukemania.midi;

import java.util.List;
import java.util.Optional;

public final class PercussionFactoryImpl implements AbstractFactory {
    private static PercussionFactoryImpl instance;
    private static final int MIN = 35;
    private static final int MAX = 81;

    private PercussionFactoryImpl() { }

    public static PercussionFactoryImpl getInstance() {
        if (instance == null) {
            instance = new PercussionFactoryImpl();
        }
        return instance;
    }

    @Override
    public AbstractNote createNote(final Optional<Long> duration, final long startTime, final int identifier) {
        if (identifier >= MIN && identifier <= MAX) {
            return new PercussionNote(duration, startTime, identifier);
        } else {
            throw new InvalidNoteException();
        }
    }

    @Override
    public ParsedTrack createTrack(final Enum<InstrumentType> instrument, final List<AbstractNote> notes,
            final int channel) {
        return new PercussionTrackImpl(notes, channel);
    }

}

