package it.dukemania.audioengine;

import it.dukemania.util.Pair;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class KeyboardSynth implements Synth {

    // create a bufferManager for a ceratin note in a certain track in a certain song
    private static BufferManager<Float> createNoteBuffer(final Enveloper env,
                                                         final float freq,
                                                         final long time,
                                                         final WaveTable[]waves,
                                                         final Function<Long, Float> noteLFO,
                                                         final Function<Long, Float> volumeLFO,
                                                         final double [] offsets) {
        double[] steps = Arrays.stream(offsets).map(x -> ((Settings.WAVETABLE_SIZE * (x * freq)) / Settings.SAMPLE_RATE)).toArray();
        final double[] positions = new double[steps.length];
        long total = (long) (time * Settings.SAMPLESPERMILLI + env.getTime() + 1000);
        double [] buff = LongStream.range(0, total).mapToDouble(
            k -> {
                float noteLfoVal = noteLFO.apply(k);
                return IntStream.range(0, steps.length).mapToDouble(x -> {
                    positions[x] = positions[x] + steps[x] * noteLfoVal;
                    return waves[x].getAt((int) (positions[x] % Settings.WAVETABLE_SIZE));
                }).sum() / steps.length * volumeLFO.apply(k) / waves.length;
            }
        ).toArray();
        return env.createBufferManager(buff);
    }

    private final Map<Integer, BufferManager<Float>> keys = new HashMap<>();

    /**
     * costructor of KeyboardSynth, usually called by a builder.
     * @param env the enveloper which all the notees of the synth must follow
     * @param waves the wave forms of the osacillators
     * @param nlfo the note lfo
     * @param vlfo the volume lfo
     * @param offsets the offsets of the oscilaltors
     * @param freqs a list of pairs, X is the note frequency, Y is the maxium duration for the note (in ms)
     */
    public KeyboardSynth(final Enveloper env,
                         final WaveTable [] waves,
                         final Function<Long, Float> nlfo,
                         final Function<Long, Float> vlfo,
                         final double [] offsets,
                         final List<Pair<Integer, Long>> freqs) {
        freqs.forEach(x -> {
            final int numA4 = 69;
            final int numNote = 12;
            final double freqA4 = 440;
            float freq = (float) (Math.pow(2, (double) (x.getX() - numA4) / numNote) * freqA4);
            keys.put(x.getX(), createNoteBuffer(env, freq, x.getY(), waves, nlfo, vlfo, offsets));
        });
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int checkKeys() {
        return (int) keys.values().stream().filter(BufferManager::hasNext).count();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public float getSample() {
        return (float) keys.values().stream().filter(BufferManager::hasNext).mapToDouble(BufferManager::next).sum();
    }
    /**
     * Given a certain frequency, play that note for a certain amount of time.
     * @param identifier the frequency of the note that wants to be played
     * @param micros how many microseconds we want the note to be played
     */
    public void playTimedNote(final int identifier, final Long micros) {
        keys.get(identifier).refresh(micros / 1000);
    }

}
