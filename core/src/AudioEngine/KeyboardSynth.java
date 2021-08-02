package AudioEngine;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class KeyboardSynth implements Synth{

    private class Note {
        public Note(Enveloper env, float freq, long time, WaveTable[]waves, Function<Long, Float> noteLFO, Function<Long, Float> volumeLFO, double [] offsets) {
            envIterator = env.createEnveloper();
            double[] steps = Arrays.stream(offsets).map(x->((Settings.WAVETABLE_SIZE * (x * freq)) / Settings.SAMPLE_RATE)).toArray();
            final double[] positions = new double[steps.length];
            long total = (long) (time * Settings.SAMPLESPERMILLI + env.getTime() + 100);
            this.buff = LongStream.range(0, total).mapToDouble(
                k-> {
                    float noteLfoVal = noteLFO.apply(k);
                    return 	IntStream.range(0, steps.length)
                            .mapToDouble(x->waves[x].getAt((int) ((positions[x] = positions[x] + steps[x] * noteLfoVal)  % Settings.WAVETABLE_SIZE)))
                            .sum() / steps.length * volumeLFO.apply(k);
                }
            ).toArray();
        }

        private int processedSamples = 0;
        private int restartPos = 0;
        private final EnveloperIterator<Float> envIterator;
        private final double[] buff;

        public float nextSample() {
            if (restartPos != 0) {
                if (processedSamples == restartPos) {
                    processedSamples = 0;
                    restartPos = 0;
                }
            }
            return (float) this.buff[this.processedSamples++] * envIterator.next();
        }

        public void playMillis(long ttl) {
            restartPos = processedSamples + Settings.ATTENUATION;
            this.envIterator.refresh(ttl);
        }
    }


    private final Map<Float, Note> keys = new HashMap<>();

    /**
     * costructor of KeyboardSynth, usually called by a builder
     * @param freqs the frequencies of the notes we want to load
     */
    public KeyboardSynth(Enveloper env, WaveTable[]waves, Function<Long, Float> nlfo, Function<Long, Float> vlfo, double [] offsets, List<Pair<Float, Long>> freqs){
        freqs.forEach(x->keys.put(x.getX(), new Note(env, x.getX(), x.getY(), waves, nlfo, vlfo, offsets)));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int checkKeys() {
        return (int) keys.values().stream().filter(x->x.envIterator.hasNext()).count();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public float getSample() {
        return (float) keys.values().stream().filter(x->x.envIterator.hasNext()).mapToDouble(Note::nextSample).sum();
    }
    /**
     * Given a certain frequency, play that note for a certain amount of time
     * @param freq the frequency of the note that wants to be played
     * @param micros how many microseconds we want the note to be played
     */
    public void playTimedNote(float freq, Long micros) {
        keys.get(freq).playMillis(micros / 1000);
    }
}
