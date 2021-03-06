package it.dukemania.audioengine;

import java.util.Arrays;

public class DrumSynth implements Synth {

    /**
     * {@inheritDoc}
     */
    @Override
    public int checkKeys() {
        return (int) Arrays.stream(DrumSamples.values()).filter(DrumSamples::hasNext).count();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public float getSample() {
        return (float) Arrays.stream(DrumSamples.values()).filter(DrumSamples::hasNext).mapToDouble(DrumSamples::next).sum();
    }
    /**
     * Start playing or restart playing a percussion.
     * @param drum the percussion
     */
    public void playPercussion(final DrumSamples drum) {
        if (drum != DrumSamples.Empty) {
            drum.refresh();
        }
    }

}
