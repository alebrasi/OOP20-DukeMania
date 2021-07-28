package AudioEngine;

import java.util.function.Function;

public class LFOFactory {

    public static Function<Long, Float> straightLineLFO(float targetMult, int duration) {
        float sampleDuration = (float)duration * Settings.SAMPLESPERMILLI;
        float step = (targetMult - 1f) / sampleDuration;
        return x -> 1 + (step * (x % sampleDuration));
    }

    public static Function<Long, Float> squareLFO(float multMax, float multMin, int duration){
        float sampleDuration = (float)duration * Settings.SAMPLESPERMILLI;
        return x -> x % sampleDuration <= sampleDuration / 2 ? multMax : multMin;
    }

}
