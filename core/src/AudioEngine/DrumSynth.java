package AudioEngine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DrumSynth implements Synth{

    enum DrumSamples{
        Kick, Snare, Hat;
    }


    private class Percussion {


    }

    private final Map<Float, Percussion> keys = new HashMap<>();
    private final Set<Float> active = new HashSet<>();

    @Override
    public int checkKeys() {
        return 0;
    }

    @Override
    public float getSample() {
        return 0f;
    }

    public void test(){

    }

}
