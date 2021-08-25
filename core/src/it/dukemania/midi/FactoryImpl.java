package it.dukemania.midi;

import java.util.List;
import java.util.Optional;

public final class FactoryImpl implements AbstractFactory {
    private static FactoryImpl instance;

    private FactoryImpl() { }

    public static FactoryImpl getInstance() {
        if (instance == null) {
            instance = new FactoryImpl();
        }
        return instance;
    }

    /**
     * @return a Note
     */
    @Override
    public AbstractNote createNote(final Optional<Long> duration, final long startTime, final int identifier) {
        return new Note(duration, startTime, identifier);
    }

    /**
     * @param instrument the instrument associated with the track
     * @return a KeyboardTrack
     */
    @Override
    public ParsedTrack createTrack(final Enum<InstrumentType> instrument, final List<AbstractNote> notes, final int channel) {
        return new KeyboardTrack(instrument, notes, channel);
    }

}
