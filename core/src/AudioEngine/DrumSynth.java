package AudioEngine;

import java.util.Arrays;

public class DrumSynth implements Synth{

    /**
     * {@inheritDoc}
     */
    @Override
    public int checkKeys() {
        return (int) Arrays.stream(DrumSamples.values()).filter(x->x.sampleBuffer.hasNext()).count();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public float getSample() {
        // TODO CONTROLLO SE CE NE E ALMENO 1 CHE PLAYA
        return (float) Arrays.stream(DrumSamples.values()).filter(x->x.sampleBuffer.hasNext()).mapToDouble(x->x.sampleBuffer.next()).sum();
    }

    /**
     * Start playing or restart playing a percussion
     * @param drum the percussion
     */
    public void playPercussion(DrumSamples drum){
        drum.sampleBuffer.refresh();
    }

}
