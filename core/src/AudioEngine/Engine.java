package AudioEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private List<KeyboardSynth> synthetizers = new ArrayList<>();
    private final AudioDevice ad = Gdx.audio.newAudioDevice((int) Settings.SAMPLE_RATE, true);
    private final float [] buffer = new float[Settings.BUFFER_LENGHT];

    public Engine(){
        SynthBuilderImpl b = new SynthBuilderImpl();
        b.setEnveloper(new Enveloper(10l, 1f, 100l));
        b.setWavetables(new WaveTable[]{WaveTable.Sine});
        b.setOffsets(new double[]{1d});

        List<Pair<Float, Long>> notes = new ArrayList<>();
        notes.add(new Pair(100f, 10000l)); // ricordarsi il / 1000 dai micros

        try {
            synthetizers.add(b.build(notes));
        } catch (Exception e) { e.printStackTrace(); }

        synthetizers.get(0).playTimedNote(100f, 1000000l);
    }

    /**
     * Calculates and plays a bufffer to the LibGDX audio device
     */
    public void playBuffer(){
        int num = synthetizers.stream().mapToInt(Synth::checkKeys).sum();
        for(int i=0;i<buffer.length;i++){
            buffer[i] = (float) (synthetizers.stream().mapToDouble(Synth::getSample).sum() * 1);
        }
        ad.writeSamples(this.buffer, 0, buffer.length);
    }
}
