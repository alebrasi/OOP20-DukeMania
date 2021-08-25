package it.dukemania.model.serializers.synthesizer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dukemania.audioengine.SynthBuilderImpl;
import it.dukemania.midi.InstrumentType;

import java.util.List;

@JsonDeserialize(using = SynthesizerInfoDeserializer.class)
@JsonSerialize(using = SynthesizerInfoSerializer.class)
public class SynthInfo {
    private final String name;
    private final SynthBuilderImpl synth;
    private final List<InstrumentType> associatedInstruments;

    public SynthInfo(final String name, final SynthBuilderImpl synth, final List<InstrumentType> associatedInstruments) {
        this.name = name;
        this.synth = synth;
        this.associatedInstruments = associatedInstruments;
    }

    /**
     * @return The synthesizer preset name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The synthesizer builder
     */
    public SynthBuilderImpl getSynth() {
        return synth;
    }

    /**
     * @return A list of associated instruments
     */
    public List<InstrumentType> getAssociatedInstruments() {
        return this.associatedInstruments;
    }
}
