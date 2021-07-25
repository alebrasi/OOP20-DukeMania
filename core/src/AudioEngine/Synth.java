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

    /**
     * Given a certain frequency, play that note for a certain amount of time
     * @param freq the frequency of the note that wants to be played
     * @param micros how many microseconds we want the note to be played
     */
    void playTimedNote(float freq, Long micros);
}
