package AudioEngine;

import sun.jvm.hotspot.debugger.remote.amd64.RemoteAMD64Thread;

import java.util.*;
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
                private final EnveloperIterator<Float> env = new Enveloper(10l, 1f, 100l).createEnveloper();
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

        static {
            Snare.sampleBuffer = getSnare();
            Hat.sampleBuffer = getHat();




        }
    }


    private final Set<DrumSamples> active = new HashSet<>();

    public DrumSynth(){
        active.add(DrumSamples.Snare);
    }

    @Override
    public int checkKeys() {
        return (int) active.stream().filter(x->x.sampleBuffer.hasNext()).count();
    }

    @Override
    public float getSample() {
        return (float) active.stream().filter(x->x.sampleBuffer.hasNext()).mapToDouble(x->x.sampleBuffer.next()).sum();
    }

    public void test(){
        active.stream().findAny().get().sampleBuffer.refresh();
    }

}
