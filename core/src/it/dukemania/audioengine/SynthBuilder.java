package it.dukemania.audioengine;

import java.util.function.Function;

public interface SynthBuilder {

    /**
     * Set the enveloper template.
     * @param env the enveloper
     */
    void setEnveloper(Enveloper env);

    /**
     * Set an LFO for the note pitch to follow.
     * @param lfo the LFO
     */
    void setNoteLFO(Function<Long, Float> lfo);

    /**
     * Set an LFO for the note volume to follow.
     * @param lfo the LFO
     */
    void setVolumeLFO(Function<Long, Float> lfo);

    /**
     * Set the waveforms of the different oscillators.
     * @param waves an array containing WaveTable enum items
     */
    void setWavetables(WaveTable[] waves);

    /**
     * Set the pitch offset for each oscillator.
     * @param offsets an array with the offset values
     */
    void setOffsets(double[] offsets);
}
