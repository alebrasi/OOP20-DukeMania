package it.dukemania.midi;

import java.util.Optional;

public class GenericNote extends Note {

    private static final int NUM_A4 = 69;
    private static final int NUM_NOTE = 12;
    private static final double FREQ_A4 = 440;
    private final double frequency;

    public GenericNote(final Optional<Double> duration, final int startTime, final int identifier) {
        super(duration, startTime, identifier);
        this.frequency = (double) (Math.pow(2, (double) (identifier - NUM_A4) / NUM_NOTE)) * FREQ_A4;
    }



    public final double getFrequency() {
        return frequency;
    }


}
