package it.dukemania.Model.serializers.synthesizer;

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

    public String getName() {
        return name;
    }

    public SynthBuilderImpl getSynth() {
        return synth;
    }

    public List<InstrumentType> getAssociatedInstruments() {
        return this.associatedInstruments;
    }
}
