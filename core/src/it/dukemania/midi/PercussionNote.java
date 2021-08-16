package it.dukemania.midi;

import java.util.Optional;

public class PercussionNote extends AbstractNote {
    private static final int OFFSET = 35;
    private final Percussion instrument;

    public PercussionNote(final Optional<Double> duration, final int startTime, final int identifier) {
        super(duration, startTime, identifier);
        this.instrument = Percussion.values()[identifier - OFFSET];
    }

    public final Percussion getInstrument() {
        return instrument;
    }


}
