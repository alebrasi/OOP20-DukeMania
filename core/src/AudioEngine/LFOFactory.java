package AudioEngine;

import java.util.function.Function;

public class LFOFactory {

    /**
     * All the available LFO types.
     */
    enum types {
        intervals, straigh, sine, square;
    }

    public static Function<Long, Float> composedLFO(final Function<Long, Float> valueFunction, final Function<Long, Float> activateFunction) {
        return x -> (valueFunction.apply(x)) * (activateFunction.apply(x));
    }

    /**
     * Generic function to create an lfo, easily callable.
     * @param type the type of the lfo
     * @param args the arguments of the lfo
     * @param duration the duration of an lfo pulse
     * @return the lfo function
     */
    public static Function<Long, Float> general(final types type, final float [] args, final int duration) {
        switch (type) {
            case intervals: return buildIntervals(args, duration);
            case straigh: return straightLineLFO(args[0], duration);
            case sine: return sineLFO(args[0], args[1], duration);
            case square: return squareLFO(args[0], args[1], duration);
            default: return x -> 1f;
        }
    }

    /**
     * create an lfo with a straight line shape.
     * @param targetMult the target multiplier frequency value
     * @param duration the duration of an lfo pulse
     * @return the lfo function
     */
    public static Function<Long, Float> straightLineLFO(final float targetMult, final int duration) {
        float sampleDuration = (float) duration * Settings.SAMPLESPERMILLI;
        float step = (targetMult - 1f) / sampleDuration;
        return x -> 1 + (step * (x % sampleDuration));
    }

    /**
     * create an lfo with a square shape, frequency will jump from freq * multmax to freq * multmin in duration ms.
     * @param multMax the high multiplier of the lfo
     * @param multMin the minium multiplier of the lfo
     * @param duration the duration of an lfo pulse
     * @return the lfo function
     */
    public static Function<Long, Float> squareLFO(final float multMax, final float multMin, final int duration) {
        float sampleDuration = (float) duration * Settings.SAMPLESPERMILLI;
        return x -> x % sampleDuration < sampleDuration / 2 ? multMax : multMin;
    }

    /**
     * create an lfo which will divide duration window in multipliers.size() segments, each with a different volume value.
     * @param multipliers the multipliers
     * @param duration the duration of an lfo pulse
     * @return the lfo function
     */
    public static Function<Long, Float> buildIntervals(final float [] multipliers, final int duration) {
        int sampleDuration = (int) (duration * Settings.SAMPLESPERMILLI);
        int single = sampleDuration / multipliers.length;
        return x -> multipliers[(int) ((x % sampleDuration) / single)];
    }

    /**
     * create an lfo which will make the frequency alter in the shape of a sine wave.
     * @param multMax the maxium multiplier
     * @param multMin the minium multiplier
     * @param duration the duration of an lfo pulse
     * @return the lfo function
     */
    public static Function<Long, Float> sineLFO(final float multMax, final float multMin, final int duration) {
        float sampleDuration = (float) duration * Settings.SAMPLESPERMILLI;
        float initFreq = (float) (1d / (sampleDuration / Settings.SAMPLE_RATE));
        float period = Settings.SAMPLE_RATE / initFreq;
        float half = (multMax - multMin) / 2;
        float start = sampleDuration / 4;
        return x -> (float) (multMin + half + Math.sin(2.0 * Math.PI * (x + start) / period) * half);
    }

}
