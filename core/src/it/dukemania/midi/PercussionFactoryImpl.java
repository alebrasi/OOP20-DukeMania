package it.dukemania.midi;

import java.util.List;
import java.util.Optional;

public final class PercussionFactoryImpl implements AbstractFactory {
    private static PercussionFactoryImpl instance;
    private static final int MIN = 35;
    private static final int MAX = 81;

    /**
     * this is a private constructor.
     */
    private PercussionFactoryImpl() { }

    /**
     * this method returns an instance of PercussionFactoryImpl, making sure that only one is istanciated.
     * @return an instance of PercussionFactoryImpl
     */
    public static PercussionFactoryImpl getInstance() {
        if (instance == null) {
            instance = new PercussionFactoryImpl();
        }
        return instance;
    }

    /**
     * @param identifier the MIDI number that identifies the Percussion associated to the note.
     */
    @Override
    public AbstractNote createNote(final Optional<Long> duration, final long startTime, final int identifier) {
        if (identifier >= MIN && identifier <= MAX) {
            return new PercussionNote(duration, startTime, identifier);
        } else {
            throw new InvalidNoteException();
        }
    }

    /**
     * @param instrument this parameter is not used
     * @return a PercussionTrack
     */
    @Override
    public ParsedTrack createTrack(final Enum<InstrumentType> instrument, final List<AbstractNote> notes,
            final int channel) {
        return new PercussionTrack(notes, channel);
    }

}

