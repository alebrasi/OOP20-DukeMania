package it.dukemania.audioengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import it.dukemania.midi.MidiTrack;
import it.dukemania.midi.TrackImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Engine {

    public final List<Synth> synthetizers = new ArrayList<>();
    private final AudioDevice ad = Gdx.audio.newAudioDevice((int) Settings.SAMPLE_RATE, true);
    private final float [] buffer = new float[Settings.BUFFER_LENGHT];

    private float step = 0, vol = 0;
    private int old = 0;
    private int att = 0;

    public Engine() {

    }
    /**
     * Calculates and plays a bufffer to the LibGDX audio device.
     */
    public void playBuffer() {

        int num = synthetizers.stream().mapToInt(Synth::checkKeys).sum();
        if (old > 0 && old != num) {
            att = 100;
            step = ((calc(num)) - vol) / att;
        } else {
            vol = calc(num);
        }

        for (int i = 0; i < Settings.BUFFER_LENGHT; i++) {
            if (att != 0) {
                vol += step;
                att--;
            }
            buffer[i] = (float) (synthetizers.stream().mapToDouble(Synth::getSample).sum()) * vol;
        }

        ad.writeSamples(buffer, 0, buffer.length);
        old = num;

    }

    public void addDrum() {
        synthetizers.add(new DrumSynth());
    }

    public void addSynth(final MidiTrack track) {
        SynthBuilderImpl b = new SynthBuilderImpl();
        b.setEnveloper(new Enveloper(10l, 1f, 100l));
        b.setWavetables(new WaveTable[]{WaveTable.Sine});
        b.setOffsets(new double[]{1f});

        List<Pair<Float, Long>> notes = new ArrayList<>();
        var actualTrack = (TrackImpl)track;



        final int NUM_A4 = 69;
        final int NUM_NOTE = 12;
        final double FREQ_A4 = 440;

        actualTrack.getNotesMaxDuration().forEach((key, value) -> {
            float frequency = (float) (Math.pow(2, (double) (key - NUM_A4) / NUM_NOTE) * FREQ_A4);
            notes.add(new Pair<>(frequency, value / 1000));
        });

        try {
            synthetizers.add(b.build(notes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float calc(final int n) {
        switch (n) {
            case 1: return 0.3f;
            case 2: return 0.25f;
            case 3: return 0.22f;
            case 4: return 0.16f;
            case 5: return 0.15f;
            default: return 1f / (float)n; //0.16f-
        }
    }
}
