package it.dukemania.audioengine;

public final class Settings {


    private static final float DEFAULT_SAMPLE_RATE = 22100;
    private static final int DEFAULT_BUFFER_SIZE = 512;

    /**
     * The available sample rates.
     */
    public static final Integer[] AVAILABLE_SAMPLE_RATES = { 11000, 22100, 44100, 128000, 192000 };
    /**
     * The available buffer sizes.
     */
    public static final Integer[] AVAILABLE_BUFFER_SIZES = { 128, 256, 512, 1024, 2048, 4096, 8192 };

    /**
     * How many samples are played every seconds.
     */
    public static float SAMPLE_RATE = DEFAULT_SAMPLE_RATE;
    /**
     * The size of each sample.
     */
    public static final float SAMPLE_SIZE = Float.SIZE;
    /**
     * The maxium default volume.
     */
    public static final double MAX_VOLUME = 1;
    /**
     * How many samples copmpose a buffer.
     */
    public static int BUFFER_LENGHT = DEFAULT_BUFFER_SIZE;
    /**
     * How many samples are played every millisecond.
     */
    public static final float SAMPLESPERMILLI = Settings.SAMPLE_RATE / 1000f;
    /**
     * The number of samples that compose a wavetable.
     */
    public static final float WAVETABLE_SIZE = 8192;
    /**
     * The number of attenuations samples, to restart the note.
     */
    public static final int ATTENUATION = 300;


    private Settings() {

    }

}
