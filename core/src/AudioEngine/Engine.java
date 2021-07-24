package AudioEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Engine {
    private List<Synth> synthetizers = new ArrayList<>();
    private AudioDevice ad = Gdx.audio.newAudioDevice((int) Settings.SAMPLE_RATE, true);
    private float [] buffer = new float[Settings.BUFFER_LENGHT];

    long pos = 0;
    float freq = 440;
    float period = Settings.SAMPLE_RATE / freq;

    public void playBuffer(){
        for(int i=0;i<buffer.length;i++){
            this.buffer[i] = (float)(Math.sin(2.0 * Math.PI * pos++ / period) * Settings.MAX_VOLUME);
        }
        ad.writeSamples(this.buffer, 0, buffer.length);
    }
}
