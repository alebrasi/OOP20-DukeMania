package it.dukemania.audioengine;

import java.util.Random;

public enum WaveTable {
    /**
     * Wave with a sine shape.
     */
    Sine,
    /**
     * Wave with a square shape, its values are comparable to binary digits.
     */
    Square,
    /**
     * Wave with a Saw Tooth form.
     */
    Saw,
    /**
     * Wave with a triangular shape.
     */
    Triangle,
    /**
     * Wave without a shape, its values are generated randomly to simulate white noise.
     */
    Noise;

    private final float [] wave = new float [(int) Settings.WAVETABLE_SIZE];


    static {
        float initFreq = (float) (1d / (Settings.WAVETABLE_SIZE / Settings.SAMPLE_RATE));
        float period = Settings.SAMPLE_RATE / initFreq;
        Random rnd = new Random();

        for (float i = 0; i < Settings.WAVETABLE_SIZE; i++) {
            Triangle.wave[(int) i] = (float) (2 / Math.PI * Math.asin(Math.sin(2 * Math.PI * i / period)) * Settings.MAX_VOLUME);
            Saw.wave[(int) i] =      (float) (-2 / Math.PI * Math.atan(1 / Math.tan(Math.PI * i / period)) * Settings.MAX_VOLUME);
            Sine.wave[(int) i] =     (float) (Math.sin(2.0 * Math.PI * i / period) * Settings.MAX_VOLUME);
            Square.wave[(int) i] =   (float) (i < period / 2 ? 1 : -1 * Settings.MAX_VOLUME);
            Noise.wave[(int) i] =    rnd.nextFloat();
        }
    }

    /**
     * get the wave buffer of a specific wave.
     * @return the sample buffer of the desired wave
     */
    public float[] getWave() {
        return wave;
    }
    /**
     * get the sample of a specific wave at a specific position.
     * @param pos the position
     * @return the sample
     */
    public float getAt(final int pos) {
        return wave[pos];
    }
}
