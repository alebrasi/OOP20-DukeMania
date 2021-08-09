package it.dukemania.audioengine;

public interface Synth {
    /**
     * Checks how many simulated simulated instrument items are currently playing
     * @return the number of items
     */
    int checkKeys();

    /**
     * Returns a single sample with the values of the combined sample in the Synthesizer
     * @return the sample
     */
    float getSample();
}
