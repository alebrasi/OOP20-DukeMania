package AudioEngine;

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
        // TODO FRATTO 3 CI VUOLE ?
        return (float) Arrays.stream(DrumSamples.values()).filter(DrumSamples::hasNext).mapToDouble(DrumSamples::next).sum();
    }
    /**
     * Start playing or restart playing a percussion.
     * @param drum the percussion
     */
    public void playPercussion(final DrumSamples drum) {
        drum.refresh();
    }

}
