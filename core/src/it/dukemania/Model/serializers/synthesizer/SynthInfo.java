package it.dukemania.Model.serializers.synthesizer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dukemania.audioengine.SynthBuilderImpl;

@JsonDeserialize(using = SynthesizerInfoDeserializer.class)
@JsonSerialize(using = SynthesizerInfoSerializer.class)
public class SynthInfo {
    private final String name;
    private final SynthBuilderImpl synth;

    public SynthInfo(final String name, final SynthBuilderImpl synth) {
        this.name = name;
        this.synth = synth;
    }

    public String getName() {
        return name;
    }

    public SynthBuilderImpl getSynth() {
        return synth;
    }
}
