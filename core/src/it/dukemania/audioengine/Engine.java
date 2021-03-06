package it.dukemania.audioengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import it.dukemania.midi.Instrument;
import it.dukemania.midi.InstrumentType;
import it.dukemania.midi.ParsedTrack;
import it.dukemania.midi.KeyboardTrack;
import it.dukemania.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private final List<Synth> synthetizers = new ArrayList<>();
    private static final AudioDevice AD = Gdx.audio.newAudioDevice((int) Settings.SAMPLE_RATE, true);
    private final float [] buffer = new float[Settings.BUFFER_LENGHT];

    private float step;
    private float vol;
    private int old;
    private int att;


    /**
     * Calculates and plays a buffer to the LibGDX audio device.
     */
    public int playBuffer() {

        final int num = synthetizers.stream().mapToInt(Synth::checkKeys).sum();
        if (old > 0 && old != num) {
            att = 100;
            step = (regulateVolume(num) - vol) / att;
        } else {
            vol = regulateVolume(num);
        }

        for (int i = 0; i < Settings.BUFFER_LENGHT; i++) {
            vol += --att > 0 ? step : 0;
            buffer[i] = (float) (synthetizers.stream().mapToDouble(Synth::getSample).sum()) * vol;
        }

        AD.writeSamples(buffer, 0, buffer.length);
        old = num;
        return num;

    }

    /**
     * Add a standard keyboard synthesizer to the synthesizer list.
     * @param track the track that the synth will play
     * @return the synth 
     */
    public Synth addSynth(final ParsedTrack track) {
        final var actualTrack = (KeyboardTrack) track;
        final List<Pair<Integer, Long>> notes = new ArrayList<>();
        actualTrack.getNotesMaxDuration().forEach((key, value) -> {
            notes.add(new Pair<>(key, value / 1000));
        });

        final Instrument serializedInstrument = new Instrument((InstrumentType) actualTrack.getInstrument());

        try {
            final var synth = serializedInstrument.getSynthetizer().build(notes);
            synthetizers.add(synth);
            return synth;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Add a drum synthesizer to the synthesizer list.
     * @return the drum synthesizer added
     */
    public Synth addDrum() {
        final var drumSynth = new DrumSynth();
        synthetizers.add(drumSynth);
        return drumSynth;
    }

    /**
     * calculate the volume multiplier.
     * @param n the number of notes that are currently playing
     * @return the volume multiplier
     */
    public float regulateVolume(final int n) {
        switch (n) {
            case 1: return 0.3f;
            case 2: return 0.25f;
            case 3: return 0.22f;
            case 4: return 0.16f;
            case 5: return 0.15f;
            default: return 1f / (float) n; //0.16f-
        }
    }
}
