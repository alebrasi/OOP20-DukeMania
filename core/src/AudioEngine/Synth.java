package AudioEngine;

public interface Synth {
    int activeKeys();
    float getSample();
    void playTimedNote(float freq, Long micros);
}
