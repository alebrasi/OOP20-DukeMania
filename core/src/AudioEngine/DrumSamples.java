package AudioEngine;

import java.util.Random;
import java.util.function.Function;

enum DrumSamples{

    /**
     * A standard Drum Kick
     */
    Kick,
    /**
     * A drum snare or hand clap
     */
    Snare,
    /**
     * A short tap on the hat
     */
    Hat,
    /**
     * A standard tom drum
     */
    Tom;
    /**
     * An iterator containing the pre-loaded drum samples
     */
    DrumIterator<Float> sampleBuffer;

    /**
     * Load the Snare sample Iterator
     * @return the Iterator
     */
    static DrumIterator<Float> getSnare(){
        Random rnd = new Random();
        float [] buff = new float [20000];
        for(int i =0;i<buff.length;i++){
            buff[i] = rnd.nextFloat();
        }
        Filters.sampleNHold(buff,10);
        return new DrumIterator<Float>() {
            private final EnveloperIterator<Float> env = new Enveloper(10l, 1f, 300l).createEnveloper();
            private int passed = 0;
            @Override
            public boolean hasNext() {
                return env.hasNext();
            }
            @Override
            public Float next() {
                return buff[passed++] * env.next();
            }
            @Override
            public void refresh() {
                this.passed = 0;
                env.refresh(10);
            }
        };
    }

    /**
     * Load the Hat sample Iterator
     * @return the Iterator
     */
    static DrumIterator<Float> getHat(){
        Random rnd = new Random();
        float [] buff = new float [20000];
        for(int i =0;i<buff.length;i++){
            buff[i] = rnd.nextFloat();
        }
        return new DrumIterator<Float>() {
            private final EnveloperIterator<Float> env = new Enveloper(10l, 1f, 25l).createEnveloper();
            private int passed = 0;
            @Override
            public boolean hasNext() {
                return env.hasNext();
            }
            @Override
            public Float next() {
                return buff[passed++] * env.next();
            }
            @Override
            public void refresh() {
                this.passed = 0;
                env.refresh(10);
            }
        };
    }

    /**
     * Load the Kick sample Iterator
     * @return the Iterator
     */
    static DrumIterator<Float> getKick(){
        double[] steps = {(Settings.WAVETABLE_SIZE * (220)) / Settings.SAMPLE_RATE, (Settings.WAVETABLE_SIZE * (55)) / Settings.SAMPLE_RATE};
        Function<Long,Float> lfoOsc1 = LFOFactory.straightLineLFO(0.1f, 80);
        double [] pos = new double[2];
        float[] snarebuff = new float[300000];
        boolean lfoEnded = false;
        float noteLfoVal = 0f;
        for(int i = 0;i<300000;i++){
            noteLfoVal = lfoEnded ? noteLfoVal : lfoOsc1.apply((long) i);
            if(noteLfoVal <= 0.11){
                lfoEnded = true;
                noteLfoVal = 0;
            }
            snarebuff[i] =  WaveTable.Square.getAt((int)((pos[0] = pos[0] + steps[0] * noteLfoVal) % Settings.WAVETABLE_SIZE)) +
                    WaveTable.Sine.getAt((int)((pos[1] = pos[1] + steps[1]) % Settings.WAVETABLE_SIZE)) ;
        }
        return new DrumIterator<Float>() {
            private final EnveloperIterator<Float> env = new Enveloper(10l, 1f, 100l).createEnveloper();
            private int passed = 0;
            @Override
            public boolean hasNext() {
                return env.hasNext();
            }
            @Override
            public Float next() {
                return snarebuff[passed++] * env.next();
            }
            @Override
            public void refresh() {
                this.passed = 0;
                env.refresh(10);
            }
        };
    }

    /**
     * Load the Tom sample Iterator
     * @return the Iterator
     */
    static DrumIterator<Float> getTom(){
        double step = (Settings.WAVETABLE_SIZE * (1000)) / Settings.SAMPLE_RATE;
        Function<Long,Float> lfoOsc1 = LFOFactory.straightLineLFO(0.01f, 160);
        float[] snarebuff = new float[300000];
        double pos = 0;
        for(int i = 0;i<300000;i++){
            snarebuff[i] =  WaveTable.Triangle.getAt((int)((pos = pos + step * lfoOsc1.apply((long) i)) % Settings.WAVETABLE_SIZE));
        }
        return new DrumIterator<Float>() {
            private final EnveloperIterator<Float> env = new Enveloper(10l, 1f, 100l).createEnveloper();
            private int passed = 0;
            @Override
            public boolean hasNext() {
                return env.hasNext();
            }
            @Override
            public Float next() {
                return snarebuff[passed++] * env.next();
            }
            @Override
            public void refresh() {
                this.passed = 0;
                env.refresh(10);
            }
        };
    }

    static {
        Snare.sampleBuffer = getSnare();
        Hat.sampleBuffer = getHat();
        Kick.sampleBuffer = getKick();
        Tom.sampleBuffer = getTom();
    }
}