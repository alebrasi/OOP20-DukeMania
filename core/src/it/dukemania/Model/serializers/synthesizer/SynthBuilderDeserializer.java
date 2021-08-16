package it.dukemania.Model.serializers.synthesizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.dukemania.audioengine.SynthBuilderImpl;

import java.io.IOException;

public class SynthBuilderDeserializer extends StdDeserializer<SynthBuilderImpl> {

    public SynthBuilderDeserializer() {
        this(null);
    }

    public SynthBuilderDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public SynthBuilderImpl deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        SynthBuilderImpl synth = new SynthBuilderImpl();

        return synth;
    }
}
