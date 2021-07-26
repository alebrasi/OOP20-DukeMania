package AudioEngine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeyboardSynth implements Synth{

    // TODO ALL NOTES MAP
    private final Set<Float> active = new HashSet<>();
    private final Enveloper env;

    /**
     * costructor of KeyboardSynth, usually called by a builder
     * @param freqs the frequencies of the notes we want to load
     */
    public KeyboardSynth(List<Float> freqs){
        env = new Enveloper(10l,1f,1000l);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int checkKeys() {
        return 0;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public float getSample() {
        return 0;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void playTimedNote(float freq, Long micros) {

    }
}
