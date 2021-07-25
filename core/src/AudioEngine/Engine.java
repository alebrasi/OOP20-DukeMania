package AudioEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Engine {
    private List<Synth> synthetizers = new ArrayList<>();
    private final AudioDevice ad = Gdx.audio.newAudioDevice((int) Settings.SAMPLE_RATE, true);
    private final float [] buffer = new float[Settings.BUFFER_LENGHT];
    private final Enveloper envTemplate = new Enveloper(100l, 1f, 5000l);
    private final Iterator<Float> env = envTemplate.createEnveloper(100l);

    long pos = 0;
    float freq = 440;
    float step = Settings.WAVETABLE_SIZE * (freq) / Settings.SAMPLE_RATE;

    /**
     * Calculates and plays a bufffer to the LibGDX audio device
     */
    public void playBuffer(){
        for(int i=0;i<buffer.length;i++){
            this.buffer[i] = env.hasNext() ? WaveTable.Sine.getAt((int) (pos % Settings.WAVETABLE_SIZE)) * env.next() : 0;
            pos += step;
        }
        ad.writeSamples(this.buffer, 0, buffer.length);
    }
}
