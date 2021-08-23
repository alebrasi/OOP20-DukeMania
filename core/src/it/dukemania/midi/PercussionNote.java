package it.dukemania.midi;

import it.dukemania.audioengine.DrumSamples;
import java.util.Optional;

public class PercussionNote extends AbstractNote {
    private static final int OFFSET = 35;
    private final Percussion instrument;

    /**
     * this is the constructor.
     * @param duration
     * @param startTime
     * @param identifier
     */
    public PercussionNote(final Optional<Long> duration, final long startTime, final int identifier) {
        super(duration, startTime, identifier);
        this.instrument = Percussion.values()[identifier - OFFSET];
    }

    /**
     * this method return the Percussion represented by this PercussionNote.
     * @return a Percussion Instrument
     */
    public final Percussion getInstrument() {
        return instrument;
    }

    @Override
    public final DrumSamples getItem() {
        return instrument.getAssociated();
    }


}
