package it.dukemania.audioengine;

import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

public enum DrumSamples implements Iterator<Float> {

    /**
     * A standard Drum Kick.
     */
    Kick,
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
    Tom;
    /**
     * An iterator containing the pre-loaded drum samples.
     */

    private BufferManager<Float> sampleBuffer;

    public void refresh() {
        sampleBuffer.refresh(10);
    }
    public boolean hasNext() {
        return sampleBuffer.hasNext();
    }
    public Float next() {
        return sampleBuffer.next();
    }

    /**
     * Load the Snare sample Iterator.
     */
    static void getSnare() {
        Random rnd = new Random();
        double [] buff = IntStream.range(0, 20000).mapToDouble(x -> rnd.nextFloat()).toArray();
        Filters.sampleNHold(buff, 10);
        Snare.sampleBuffer = new Enveloper(10l, 1f, 300l).createBufferManager(buff);
    }

    /**
     * Load the Hat sample Iterator.
     */
    static void getHat() {
        Random rnd = new Random();
        double [] buff = IntStream.range(0, 7000).mapToDouble(x -> rnd.nextFloat()).toArray();
        Hat.sampleBuffer = new Enveloper(10l, 1f, 25l).createBufferManager(buff);
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
            buff[i] = WaveTable.Square.getAt((int) ((pos = pos + step * lfoOsc1.apply((long) i)) % Settings.WAVETABLE_SIZE));
        }
        Kick.sampleBuffer = new Enveloper(10l, 1f, 100l).createBufferManager(buff);
    }

    /**
     * Load the Tom sample Iterator.
     */
    static void getTom() {
        double pos = 0;
        final double step = (Settings.WAVETABLE_SIZE * (1000)) / Settings.SAMPLE_RATE;
        Function<Long, Float> lfoOsc1 = LFOFactory.straightLineLFO(0.01f, 160);
        double[] buff = new double[10000];
        for (int i = 0; i < buff.length; i++) {
            buff[i] = WaveTable.Triangle.getAt((int) ((pos = pos + step * lfoOsc1.apply((long) i)) % Settings.WAVETABLE_SIZE));
        }
        Tom.sampleBuffer = new Enveloper(10l, 1f, 100l).createBufferManager(buff);
    }

    static {
        getSnare();
        getHat();
        getKick();
        getTom();
    }
}
