package AudioEngine;

import java.util.Arrays;

public class DrumSynth implements Synth{

    @Override
    public int checkKeys() {
        return (int) Arrays.stream(DrumSamples.values()).filter(x->x.sampleBuffer.hasNext()).count();
    }

    @Override
    public float getSample() {
        return (float) Arrays.stream(DrumSamples.values()).filter(x->x.sampleBuffer.hasNext()).mapToDouble(x->x.sampleBuffer.next()).sum();
    }

    public void playPercussion(DrumSamples drum){
        drum.sampleBuffer.refresh();
    }

}
