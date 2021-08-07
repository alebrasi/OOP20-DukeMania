package AudioEngine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SynthBuilderImpl implements SynthBuilder {

    private Enveloper env;
    private WaveTable[] waves;
    private double [] offsets;
    private Optional<Function<Long, Float>> noteLFO = Optional.empty();
    private Optional<Function<Long, Float>> volumeLFO = Optional.empty();
    /**
     * {@inheritDoc}
     */
    @Override
    public void setEnveloper(final Enveloper env) {
        this.env = env;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setWavetables(final WaveTable[] waves) {
        this.waves = waves;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setOffsets(final double[] offsets) {
        this.offsets = offsets;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setNoteLFO(final Function<Long, Float> lfo) {
        this.noteLFO = Optional.ofNullable(lfo);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setVolumeLFO(final Function<Long, Float> lfo) {
        this.volumeLFO = Optional.ofNullable(lfo);
    }

    /**
     * Create and return a KeyboardSynthesizer with the parameters set.
     * @param freqs the notes we want to load in the Synthesizer
     * @return the Synthesizer
     * @throws Exception Some of the Builder fields were not set properly
     */
    public KeyboardSynth build(final List<Pair<Float, Long>> freqs) throws Exception {
        if (env == null || waves == null || offsets == null) {
            throw new Exception("offsets, enveloper or wavetables are null");
        }
        if (waves.length != offsets.length) {
            throw new Exception("wavetables and offsets do not match");
        }
        return new KeyboardSynth(
                this.env,
                this.waves,
                this.noteLFO.orElse(x -> 1f),
                this.volumeLFO.orElse(x -> 1f),
                this.offsets,
                freqs
        );
    }

}
