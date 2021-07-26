package AudioEngine;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SynthBuilderImpl implements SynthBuilder{

    private Enveloper env;
    private WaveTable[] waves;
    private Optional<Function<Long, Float>> noteLFO = Optional.empty();
    private Optional<Function<Long, Float>> volumeLFO = Optional.empty();
    private Optional<double []> offsets = Optional.empty();
    /**
     * {@inheritDoc}
     */
    @Override
    public void setEnveloper(Enveloper env) {
        this.env = env;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setWavetables(WaveTable[] waves) {
        this.waves = waves;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setOffsets(double[] offsets) {
        this.offsets = Optional.ofNullable(offsets);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setNoteLFO(Function<Long, Float> lfo) {
        this.noteLFO = Optional.ofNullable(lfo);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setVolumeLFO(Function<Long, Float> lfo) {
        this.volumeLFO = Optional.ofNullable(lfo);
    }

    /**
     * Create and return a KeyboardSynthesizer with the parameters set
     * @param freqs the notes we want to load in the Synthesizer
     * @return the Synthesizer
     * @throws Exception Some of the Builder fields were not set properly
     */
    public KeyboardSynth build(List<Float> freqs) throws Exception{
        if(env == null || waves == null){
            throw new Exception("enveloper or wavetables are null");
        }
        if(!(this.offsets.get() == null) && waves.length != offsets.get().length){
            throw new Exception("wavetables and offsets do not match");
        }
        return new KeyboardSynth(freqs);
    }

}
