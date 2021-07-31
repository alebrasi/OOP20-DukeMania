package AudioEngine;

import sun.jvm.hotspot.debugger.remote.amd64.RemoteAMD64Thread;

import java.util.*;
import java.util.function.Function;
import java.util.logging.Filter;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class DrumSynth implements Synth{

    enum DrumSamples{
        Kick, Snare, Hat;
        DrumIterator<Float> sampleBuffer;



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

        static DrumIterator<Float> getKick(){
            Random rnd = new Random();

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

        static {
            Snare.sampleBuffer = getSnare();
            Hat.sampleBuffer = getHat();
            Kick.sampleBuffer = getKick();
        }
    }


    private final List<DrumSamples> active = new ArrayList<>();

    public DrumSynth(){
        active.add(DrumSamples.Kick);
        active.add(DrumSamples.Snare);
        active.add(DrumSamples.Hat);
    }

    @Override
    public int checkKeys() {
        return (int) active.stream().filter(x->x.sampleBuffer.hasNext()).count();
    }

    @Override
    public float getSample() {
        return (float) active.stream().filter(x->x.sampleBuffer.hasNext()).mapToDouble(x->x.sampleBuffer.next()).sum();
    }

    public void test(int pos){
        active.get(pos).sampleBuffer.refresh();
    }

}
