package it.dukemania.midi;

import it.dukemania.audioengine.DrumSamples;
import java.util.Optional;

public class PercussionNote extends AbstractNote {
    private static final int OFFSET = 35;
    private final Percussion instrument;

    /**
     * this is the constructor.
     * @param duration duration of the not in microseconds
     * @param startTime microsecond at which the song start
     * @param identifier number that identifies the percussion represented by this note
     */
    public PercussionNote(final Optional<Long> duration, final long startTime, final int identifier) {
        super(duration, startTime, identifier);
        this.instrument = Percussion.values()[identifier - OFFSET];
    }

    /**
     * this method return the Percussion represented by this PercussionNote.
     * @return a Percussion representing a percussion instrument
     */
    public final Percussion getInstrument() {
        return instrument;
    }

    /**
     * this method returns the DrumSample associated to the note.
     * @return the associated DrumSample
     */
    @Override
    public final DrumSamples getItem() {
        return instrument.getAssociated();
    }


}
