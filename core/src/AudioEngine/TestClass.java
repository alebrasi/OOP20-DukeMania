package AudioEngine;
import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class TestClass {

    final double TOLERANCE = 1e-5;

    private boolean checkTolerance(double actual, double exact) {
        return (Math.abs(actual - exact) < TOLERANCE);
    }

    @Test
    void drumTest() {
        DrumSynth ds = new DrumSynth();

        // test if the synth registers the play drum input properly
        ds.playPercussion(DrumSamples.Snare);
        ds.playPercussion(DrumSamples.Hat);
        Assertions.assertEquals(2, ds.checkKeys());

        // since we start from 0, the sum of the first <Settings.ATTENUATION> samples should be 0.
        // those samples are used to attenuate the note and bring it from the actual volume, where it got the input to be replayed, to 0
        Assertions.assertEquals(0.0, IntStream.range(0, Settings.ATTENUATION).mapToDouble(x -> ds.getSample()).sum());
        // after the attenuation samples, the value to put in the buffer has to be > 0
        Assertions.assertTrue(ds.getSample() > 0.0);

        // wait 1000000 samples, after those all the drums should have stopped playing and checkKeys has to return 0
        LongStream.range(0, 1000000).forEach(x -> ds.getSample());
        Assertions.assertEquals(0, ds.checkKeys());

        ds.playPercussion(DrumSamples.Kick);
        IntStream.range(0, 1000).forEach(x -> System.out.println(ds.getSample()));
        ds.playPercussion(DrumSamples.Kick);
        Assertions.assertTrue(IntStream.range(0, Settings.ATTENUATION - 1).mapToDouble(x -> ds.getSample()).sum() != 0);
        Assertions.assertTrue(checkTolerance(ds.getSample(), 0.0));

    }
}
