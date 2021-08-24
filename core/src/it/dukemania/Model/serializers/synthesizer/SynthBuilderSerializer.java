package it.dukemania.Model.serializers.synthesizer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.dukemania.audioengine.SynthBuilderImpl;

import java.io.IOException;

public class SynthBuilderSerializer extends StdSerializer<SynthBuilderImpl> {

    /**
     * 
     */
    private static final long serialVersionUID = -4194353165221206372L;

    public SynthBuilderSerializer() {
        this(null);
    }

    protected SynthBuilderSerializer(final Class<SynthBuilderImpl> t) {
        super(t);
    }

    @Override
    public void serialize(final SynthBuilderImpl value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException {

        float[] args = {0.2f, 3.2f};

        gen.writeStartObject();
        gen.writeObjectField("enveloper", value.getEnv());
        gen.writeObjectField("wavetables", value.getWaves());
        gen.writeObjectField("offsets", value.getOffsets());
        gen.writeObjectFieldStart("LFO");
        gen.writeObjectField("type", "straigh");
        gen.writeObjectField("arguments", args);
        gen.writeObjectField("duration", 2);
        gen.writeEndObject();
        gen.writeEndObject();
    }
}
