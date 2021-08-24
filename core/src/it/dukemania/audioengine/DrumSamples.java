package it.dukemania.audioengine;

import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

public enum DrumSamples implements Iterator<Float> {

    /**
     * A standard Drum Kick.
     */
    Bass,
    /**
     * A drum snare or hand clap.
     */
    Snare,
    /**
     * A short tap on the hat.
     */
    Hat,
    /**
     * A standard tom drum.
     */
    Tom,
    /**
     * Empty buffer for unused.
     */
    Empty;

    /**
     * An iterator containing the pre-loaded drum samples.
     */
    private BufferManager<Float> sampleBuffer;

    /**
     * Replay the Buffer Manager with a very small ttl.
     */
    public void refresh() {
        sampleBuffer.refresh(10);
    }

    /**
     * Checks if there can be played another sample.
     * @return true if another sample can be played, false otherwise.
     */
    public boolean hasNext() {
        return sampleBuffer.hasNext();
    }

    /**
     * Returns the next sample.
     * @return the sample
     */
    public Float next() {
        return sampleBuffer.next();
    }

    /**
     * Load the Snare sample Iterator.
     */
    static void getSnare() {
        final Random rnd = new Random();
        final double [] buff = IntStream.range(0, 20000).mapToDouble(x -> rnd.nextFloat()).toArray();
        Filters.sampleNHold(buff, 10);
        Snare.sampleBuffer = new Enveloper(10L, 1f, 300L).createBufferManager(buff);
    }

    /**
     * Load the Hat sample Iterator.
     */
    static void getHat() {
        final Random rnd = new Random();
        final double [] buff = IntStream.range(0, 7000).mapToDouble(x -> rnd.nextFloat()).toArray();
        Hat.sampleBuffer = new Enveloper(10L, 1f, 25L).createBufferManager(buff);
    }

    /**
     * Load the Kick sample Iterator.
     */
    static void getKick() {
        double pos = 0;
        final double step = (Settings.WAVETABLE_SIZE * (220)) / Settings.SAMPLE_RATE;
        final Function<Long, Float> lfoOsc1 = LFOFactory.straightLineLFO(0.1f, 80);
        double [] buff = new double[7000];
        for (int i = 0; i < buff.length; i++) {
            pos = pos + step * lfoOsc1.apply((long) i);
            buff[i] = WaveTable.Square.getAt((int) (pos % Settings.WAVETABLE_SIZE));
        }
        Bass.sampleBuffer = new Enveloper(10L, 1f, 100L).createBufferManager(buff);
    }

    /**
     * Load the Tom sample Iterator.
     */
    static void getTom() {
        double pos = 0;
        final double step = (Settings.WAVETABLE_SIZE * (1000)) / Settings.SAMPLE_RATE;
        final Function<Long, Float> lfoOsc1 = LFOFactory.straightLineLFO(0.01f, 160);
        double[] buff = new double[10000];
        for (int i = 0; i < buff.length; i++) {
            pos = pos + step * lfoOsc1.apply((long) i);
            buff[i] = WaveTable.Triangle.getAt((int) (pos % Settings.WAVETABLE_SIZE));
        }
        Tom.sampleBuffer = new Enveloper(10L, 1f, 100L).createBufferManager(buff);
    }

    /**
     * Load the empty drumSample.
     */
    static void getEmpty() {
        final double[] buff = new double[10000];
        Empty.sampleBuffer = new Enveloper(10L, 1f, 100L).createBufferManager(buff);
    }

    static {
        getSnare();
        getHat();
        getKick();
        getTom();
        getEmpty();
    }
}
