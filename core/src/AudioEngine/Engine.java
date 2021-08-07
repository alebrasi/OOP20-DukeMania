package AudioEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private List<KeyboardSynth> synthetizers = new ArrayList<>();
    private final AudioDevice ad = Gdx.audio.newAudioDevice((int) Settings.SAMPLE_RATE, true);
    private final float [] buffer = new float[Settings.BUFFER_LENGHT];
    private final DrumSynth ds = new DrumSynth();

    public Engine(){

        SynthBuilderImpl b = new SynthBuilderImpl();
        b.setEnveloper(new Enveloper(10l, 1f, 100l));
        b.setWavetables(new WaveTable[]{WaveTable.Sine,WaveTable.Sine,WaveTable.Sine});
        b.setOffsets(new double[]{1d,1.02f, 0.999f});

        List<Pair<Float, Long>> notes = new ArrayList<>();
        notes.add(new Pair(100f, 10000l)); // ricordarsi il / 1000 dai micros

        try {
            synthetizers.add(b.build(notes));
        } catch (Exception e) { e.printStackTrace(); }



    }

    long pos = 0;

    /**
     * Calculates and plays a bufffer to the LibGDX audio device
     */
    public void playBuffer(){


        /*
        if(pos++ % 100 == 0){
            synthetizers.get(0).playTimedNote(100f, 100000l);
        }
         */

        if (pos == 0 || pos == 30  || pos == 60||  pos == 90 || pos == 180 || pos == 195+15) {
            ds.playPercussion(DrumSamples.Kick);
        }
        if (pos == 15 || pos == 15+7 || pos == 60 || pos == 75 || pos == 82 || pos == 120 || pos == 150 || pos == 195 || pos == 195 + 7) {
            ds.playPercussion(DrumSamples.Hat);
        }
        if (pos == 45 || pos == 165 || pos == 225) {
            ds.playPercussion(DrumSamples.Snare);
        }
        if (pos == 105  || pos == 135) {
            ds.playPercussion(DrumSamples.Tom);
        }

        pos = (pos + 1) % 240;

        /*
        if(pos == 15 || pos == 45 || pos == 60 || pos == 52 || pos == 67 || pos == 105){
            ds.playPercussion(DrumSamples.Hat);
        }
        if(pos == 30 || pos == 90){
            ds.playPercussion(DrumSamples.Snare);
        }
         */


        /*
        int num = synthetizers.stream().mapToInt(Synth::checkKeys).sum();
        System.out.println(num);
        for(int i=0;i<buffer.length;i++){
            buffer[i] = (float) (synthetizers.stream().mapToDouble(Synth::getSample).sum() * 1);
        }
         */

        System.out.println(ds.checkKeys());
        for(int i=0;i<buffer.length;i++){
            buffer[i] = ds.getSample();
        }



        //System.out.println(num);


        ad.writeSamples(this.buffer, 0, buffer.length);
    }
}
