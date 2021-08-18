package it.dukemania.Model.serializers.synthesizer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SynthesizerInfoSerializer extends StdSerializer<SynthInfo> {

    public SynthesizerInfoSerializer() {
        this(null);
    }
    public SynthesizerInfoSerializer(final Class<SynthInfo> t) {
        super(t);
    }

    @Override
    public void serialize(final SynthInfo value, final JsonGenerator gen, final SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("presetName", value.getName());
        gen.writeObjectField("associatedInstruments", value.getAssociatedInstruments());
        gen.writeObjectField("values", value.getSynth());
        gen.writeEndObject();
    }
}
