package it.dukemania.midi;

import it.dukemania.audioengine.DrumSamples;
import java.util.Optional;

public class PercussionNote extends AbstractNote {
    private static final int OFFSET = 35;
    private final Percussion instrument;

    public PercussionNote(final Optional<Long> duration, final long startTime, final int identifier) {
        super(duration, startTime, identifier);
        this.instrument = Percussion.values()[identifier - OFFSET];
    }

    public final Percussion getInstrument() {
        return instrument;
    }

    @Override
    public final DrumSamples getItem() {
        return instrument.getAssociated();
    }


}
