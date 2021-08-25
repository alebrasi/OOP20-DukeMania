package it.dukemania.audioengine;

public final class Settings {


    /**
     * How many samples are played every seconds.
     */
    public static final float SAMPLE_RATE = 11000;
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
    public static final int BUFFER_LENGHT = 512;
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
