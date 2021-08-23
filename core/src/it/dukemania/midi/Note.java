package it.dukemania.midi;

import java.util.Optional;

public class Note extends AbstractNote {
    private static final int NUM_A4 = 69;
    private static final int NUM_NOTE = 12;
    private static final double FREQ_A4 = 440;
    private final double frequency;

    /**
     * this is the constructor.
     * @param duration
     * @param startTime
     * @param identifier
     */
    public Note(final Optional<Long> duration, final long startTime, final int identifier) {
        super(duration, startTime, identifier);
        this.frequency = (double) (Math.pow(2, (double) (identifier - NUM_A4) / NUM_NOTE)) * FREQ_A4;
    }


    /**
     * this method return the note frequency.
     * @return ferquency
     */
    public final double getFrequency() {
        return frequency;
    }



    @Override
    public final Integer getItem() {
        return super.getIdentifier();
    }


}
