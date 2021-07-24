package AudioEngine;

public class Settings {
    // audio device settings
    public static final float SAMPLE_RATE = 44100;
    public static final float SAMPLE_SIZE = Float.SIZE;
    public static final double MAX_VOLUME = 1;
    public static final int BUFFER_LENGHT = 512;
    public static final float sr = Settings.SAMPLE_RATE / 1000f;

    // wavetable
    public static final float WAVETABLE_SIZE = 8192;
}
