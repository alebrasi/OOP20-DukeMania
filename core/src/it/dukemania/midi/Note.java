package it.dukemania.midi;

import java.util.Optional;

public class Note extends AbstractNote {
    private static final int NUM_A4 = 69;
    private static final int NUM_NOTE = 12;
    private static final double FREQ_A4 = 440;
    private final double frequency;

    /**
     * this is a constructor.
     * @param duration the duration of the note in microseconds
     * @param startTime the microsecond in which the note starts
     * @param identifier the MIDI number of the note which identifies its frequency
     */
    public Note(final Optional<Long> duration, final long startTime, final int identifier) {
        super(duration, startTime, identifier);
        this.frequency = (double) (Math.pow(2, (double) (identifier - NUM_A4) / NUM_NOTE)) * FREQ_A4;
    }


    /**
     * this method return the note frequency.
     * @return the note frequency
     */
    public final double getFrequency() {
        return frequency;
    }


    /**
     * this method returns an integer representing the note identifier.
     * @return the note identifier, which identifies the note frequency
     */
    @Override
    public final Integer getItem() {
        return super.getIdentifier();
    }


}
