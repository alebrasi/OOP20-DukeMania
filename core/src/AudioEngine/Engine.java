package AudioEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private List<Synth> synthetizers = new ArrayList<>();
    private AudioDevice ad = Gdx.audio.newAudioDevice((int) Settings.SAMPLE_RATE, true);
    private float [] buffer = new float[Settings.BUFFER_LENGHT];

    long pos = 0;
    float freq = 440;
    float step = Settings.WAVETABLE_SIZE * (freq) / Settings.SAMPLE_RATE;

    public void playBuffer(){
        for(int i=0;i<buffer.length;i++){
            this.buffer[i] = WaveTable.Sine.getAt((int) (pos % Settings.WAVETABLE_SIZE));
            pos += step;
        }
        ad.writeSamples(this.buffer, 0, buffer.length);
    }
}
