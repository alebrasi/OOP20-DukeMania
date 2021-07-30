package AudioEngine;

public interface Synth {
    /**
     * Checks and stops playing the keys which enveloper has ended
     * @return the number of the notes that are still playing
     */
    int checkKeys();

    /**
     * Returns a single sample with the values of the combined notes in the Synthesizer
     * @return the sample
     */
    float getSample();
}
