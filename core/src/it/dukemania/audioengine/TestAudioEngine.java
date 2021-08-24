package it.dukemania.audioengine;
import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.dukemania.util.Pair;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class TestAudioEngine {

    private boolean checkTolerance(final double actual, final double exact) {
        double tolerance = 1e-4;
        return Math.abs(actual - exact) < tolerance;
    }

    @Test
    public void drumSynthTest() {
        DrumSynth ds = new DrumSynth();

        // test if the synth registers the play drum input properly
        ds.playPercussion(DrumSamples.Snare);
        ds.playPercussion(DrumSamples.Hat);
        Assertions.assertEquals(2, ds.checkKeys());

        // since we start from 0, the sum of the first <Settings.ATTENUATION> samples should be 0.
        // those samples are used to attenuate the note and bring it from the actual volume,
        //where it got the input to be replayed, to 0
        Assertions.assertEquals(0.0, IntStream.range(0, Settings.ATTENUATION).mapToDouble(x -> ds.getSample()).sum());
        // after the attenuation samples, the value to put in the buffer has to be > 0
        Assertions.assertTrue(ds.getSample() > 0.0);

        // wait 1000000 samples, after those all the drums should have stopped playing and checkKeys has to return 0
        LongStream.range(0, 1000000).forEach(x -> ds.getSample());
        Assertions.assertEquals(0, ds.checkKeys());

        ds.playPercussion(DrumSamples.Bass);
        IntStream.range(0, 1000).forEach(x -> ds.getSample());
        ds.playPercussion(DrumSamples.Bass);
        Assertions.assertTrue(IntStream.range(0, Settings.ATTENUATION - 1).mapToDouble(x -> ds.getSample()).sum() != 0);
        Assertions.assertTrue(checkTolerance(ds.getSample(), 0.0));
        Assertions.assertTrue(ds.getSample() > 0.0);
    }

    @Test
    public void testBuilder() {
        SynthBuilderImpl builder = new SynthBuilderImpl();

        builder.setWavetables(new WaveTable[]{WaveTable.Saw});
        builder.setOffsets(new double[]{1.0, 2.0});
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            builder.build(new ArrayList<>());
        });
        String expectedMessage = "offsets, enveloper or wavetables are null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        builder.setEnveloper(new Enveloper(100l, 1f, 100l));
        exception = Assertions.assertThrows(Exception.class, () -> {
            builder.build(new ArrayList<>());
        });
        expectedMessage = "wavetables and offsets do not match";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        builder.setWavetables(new WaveTable[]{WaveTable.Saw, WaveTable.Square});
        Assertions.assertDoesNotThrow(() -> builder.build(new ArrayList<>()));
    }

    @Test
    public void testKeyboardSynth() throws Exception {
        final float atkVol = 0.8f;
        SynthBuilderImpl builder = new SynthBuilderImpl();
        Enveloper env = new Enveloper(100l, atkVol, 100l);
        builder.setEnveloper(env);
        builder.setWavetables(new WaveTable[]{WaveTable.Saw});
        builder.setOffsets(new double[]{1});
        List<Pair<Integer, Long>> notes = new ArrayList<>();
        notes.add(new Pair<>(100, 10000l));
        notes.add(new Pair<>(200, 100000l));
        KeyboardSynth ks = builder.build(notes);

        Assertions.assertEquals(0, ks.checkKeys());
        ks.playTimedNote(100, 1000000l);
        Assertions.assertEquals(1, ks.checkKeys());
        Assertions.assertEquals(0, IntStream.range(0, Settings.ATTENUATION).mapToDouble(x -> ks.getSample()).sum());
        Assertions.assertTrue(ks.getSample() != 0);

        List<Double> allValues = IntStream.range(0, 300000).mapToDouble(x -> ks.getSample()).boxed().collect(Collectors.toList());
        double max = allValues.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double min = allValues.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        Assertions.assertTrue(max <= atkVol && min >= -atkVol);

        IntStream.range(0, 10000).forEach(x -> ks.getSample());
        Assertions.assertEquals(0, ks.checkKeys());
    }

    @Test
    public void testStraightLFO() {
        final float targetMult = 1.5f;
        final Function<Long, Float> lfo = LFOFactory.straightLineLFO(targetMult, 500);
        final long totalSamples = (long) (500 * Settings.SAMPLESPERMILLI);
        final double [] arrStraight = LongStream.range(0, totalSamples).mapToDouble(lfo::apply).toArray();
        Assertions.assertTrue(IntStream.range(0, arrStraight.length - 1).noneMatch(i -> arrStraight[i] > arrStraight[i + 1]));
        Assertions.assertEquals(1.0, lfo.apply(totalSamples), 0.0);
        Assertions.assertTrue(checkTolerance(lfo.apply(totalSamples - 1), targetMult));
    }

    @Test
    public void testSquareLFO() {
        final float multMax = 1.5f, multmin = 0.8f;
        final Function<Long, Float> lfo = LFOFactory.squareLFO(multMax, multmin, 1000);
        final long totalSamples = (long) (1000 * Settings.SAMPLESPERMILLI);
        final List<Double> arrSquare = LongStream.range(0, totalSamples)
                .mapToDouble(lfo::apply).boxed().collect(Collectors.toList());
        final Map<Double, Long> freqCounter = arrSquare.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        Assertions.assertTrue(freqCounter.values().stream().allMatch(x -> x == totalSamples / 2));
    }

    @Test
    public void testSineLFO() {
        final float multMax = 2f, multMin = 1.5f;
        final Function<Long, Float> lfo = LFOFactory.sineLFO(multMax, multMin, 2000);
        final long totalSamples = (long) (2000 * Settings.SAMPLESPERMILLI);
        final List<Double> arrSine = LongStream.range(0, totalSamples)
                .mapToDouble(lfo::apply).boxed().collect(Collectors.toList());
        Assertions.assertEquals(multMax, arrSine.get(0));
        Assertions.assertEquals(multMin, arrSine.get((int) (totalSamples / 2)));
        Assertions.assertEquals(multMax, arrSine.get((int) totalSamples - 1));
        Assertions.assertTrue(arrSine.get((int) (totalSamples / 4)) > multMin || arrSine.get((int) (totalSamples / 4)) < multMax);
        Assertions.assertTrue(arrSine.get((int) (totalSamples / multMin)) < -multMin
                || arrSine.get((int) (totalSamples / multMin)) > -multMax);
    }

    @Test
    public void testIntervalLFO() {
        final float int1 = 1f, int2 = 0.3f, int3 = 1f, int4 = 1.5f;
        final Function<Long, Float> lfo = LFOFactory.buildIntervals(new float [] {int1, int2, int3, int4}, 1000);
        final long totalSamples = (long) Settings.SAMPLE_RATE;
        final List<Double> arrIntervals = LongStream.range(0, totalSamples)
                .mapToDouble(lfo::apply).boxed().collect(Collectors.toList());
        Assertions.assertEquals(int1, arrIntervals.get(0));
        Assertions.assertEquals(int2, arrIntervals.get((int) (totalSamples / 4)));
        Assertions.assertEquals(int3, arrIntervals.get((int) (totalSamples / 2)));
        Assertions.assertEquals(int4, arrIntervals.get((int) (totalSamples - 1)));
    }
}
