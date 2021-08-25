package it.dukemania.Model.serializers.synthesizer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SynthesizerInfoSerializer extends StdSerializer<SynthInfo> {

    /**
     * 
     */
    private static final long serialVersionUID = -5380652927214390448L;

    public SynthesizerInfoSerializer() {
        this(null);
    }
    public SynthesizerInfoSerializer(final Class<SynthInfo> t) {
        super(t);
    }

    @Override
    public final void serialize(final SynthInfo value, final JsonGenerator gen, final SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("presetName", value.getName());
        gen.writeObjectField("associatedInstruments", value.getAssociatedInstruments());
        gen.writeObjectField("values", value.getSynth());
        gen.writeEndObject();
    }
}
