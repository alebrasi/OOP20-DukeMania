package it.dukemania.Controller.option;

public interface OptionWindowController {
    /**
     * Sets the current player name.
     * @param name The current player name
     */
    void setPlayerName(String name);

    /**
     * Gets the current player name.
     * @return The current player name
     */
    String getPlayerName();

    /**
     * Returns the available sample rates.
     * @return An array of the available sample rates
     */
    Integer[] getAvailableSampleRates();

    /**
     * Returns the available buffer sizes.
     * @return An array of the available buffer sizes
     */
    Integer[] getBufferSizes();

    /**
     * Sets the sample rate.
     * @param sampleRate The sample rate
     */
    void setSampleRate(int sampleRate);

    /**
     * Sets the buffer size.
     * @param bufferSize The buffer size
     */
    void setBufferSize(int bufferSize);

    /**
     * Gets the current sample rate.
     * @return The current sample rate
     */
    int getCurrentSampleRate();

    /**
     * Gets the current buffer size.
     * @return The current buffer size
     */
    int getCurrentBufferSize();
}