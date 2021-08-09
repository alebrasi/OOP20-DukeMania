package it.dukemania.audioengine;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class KeyboardSynth implements Synth {

    private class Note {
        Note(final Enveloper env, final float freq, final long time, final WaveTable[]waves, final Function<Long, Float> noteLFO, final Function<Long, Float> volumeLFO, final double [] offsets) {
            double[] steps = Arrays.stream(offsets).map(x -> ((Settings.WAVETABLE_SIZE * (x * freq)) / Settings.SAMPLE_RATE)).toArray();
            final double[] positions = new double[steps.length];
            long total = (long) (time * Settings.SAMPLESPERMILLI + env.getTime() + 100);
            double [] buff = LongStream.range(0, total).mapToDouble(
                k -> {
                    float noteLfoVal = noteLFO.apply(k);
                    return IntStream.range(0, steps.length).mapToDouble(x -> {
                        positions[x] = positions[x] + steps[x] * noteLfoVal;
                        return waves[x].getAt((int) (positions[x] % Settings.WAVETABLE_SIZE));
                    }).sum() / steps.length * volumeLFO.apply(k);
                }
            ).toArray();
            envIterator = env.createBufferManager(buff);
        }

        private final BufferManager<Float> envIterator;

        public float nextSample() {
            return envIterator.next();
        }

        public void playMillis(final long ttl) {
            this.envIterator.refresh(ttl);
        }
    }


    private final Map<Float, Note> keys = new HashMap<>();

    /**
     * costructor of KeyboardSynth, usually called by a builder.
     * @param env the enveloper which all the notees of the synth must follow
     * @param waves the wave forms of the osacillators
     * @param nlfo the note lfo
     * @param vlfo the volume lfo
     * @param offsets the offsets of the oscilaltors
     * @param freqs a list of pairs, X is the note frequency, Y is the maxium duration for the note (in ms)
     */
    public KeyboardSynth(final Enveloper env, final WaveTable [] waves, final Function<Long, Float> nlfo, final Function<Long, Float> vlfo, final double [] offsets, final List<Pair<Float, Long>> freqs) {
        freqs.forEach(x -> keys.put(x.getX(), new Note(env, x.getX(), x.getY(), waves, nlfo, vlfo, offsets)));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int checkKeys() {
        return (int) keys.values().stream().filter(x -> x.envIterator.hasNext()).count();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public float getSample() {
        return (float) keys.values().stream().filter(x -> x.envIterator.hasNext()).mapToDouble(Note::nextSample).sum();
    }
    /**
     * Given a certain frequency, play that note for a certain amount of time.
     * @param freq the frequency of the note that wants to be played
     * @param micros how many microseconds we want the note to be played
     */
    public void playTimedNote(final float freq, final Long micros) {
        keys.get(freq).playMillis(micros / 1000);
    }
}
