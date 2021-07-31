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
        b.setWavetables(new WaveTable[]{WaveTable.Sine});
        b.setOffsets(new double[]{1d});

        List<Pair<Float, Long>> notes = new ArrayList<>();
        notes.add(new Pair(100f, 50000l)); // ricordarsi il / 1000 dai micros

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
        int num = synthetizers.stream().mapToInt(Synth::checkKeys).sum();
        for(int i=0;i<buffer.length;i++){
            buffer[i] = (float) (synthetizers.stream().mapToDouble(Synth::getSample).sum() * 1);
        }
        if(pos++ % 10 == 0){
            synthetizers.get(0).playTimedNote(100f, 5l);
        }
        */

        if(pos == 0 || pos == 75 ){
            ds.test(0);
        }
        if(pos == 15 || pos == 45 || pos == 60 || pos == 52 || pos == 67 || pos == 105){
            ds.test(2);
        }
        if(pos == 30 || pos == 90){
            ds.test(1);
        }


        pos = (pos +1) % 120;

        //int num = ds.checkKeys();
        for(int i=0;i<buffer.length;i++){
            buffer[i] = ds.getSample();
        }



        ad.writeSamples(this.buffer, 0, buffer.length);
    }
}
