package it.dukemania.Model.serializers.synthesizer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.dukemania.audioengine.Enveloper;

import java.io.IOException;

public class EnveloperSerializer extends StdSerializer<Enveloper> {

    public EnveloperSerializer() {
        this(null);
    }

    public EnveloperSerializer(final Class<Enveloper> t) {
        super(t);
    }

    @Override
    public void serialize(final Enveloper value, final JsonGenerator gen, final SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        //gen.writeObjectField("attackMS", value.getAtk());
        //gen.writeObjectField("attackVolume", value.getAtkVol());
        //gen.writeObjectField("releaseMS", value.getRel());
        gen.writeEndObject();
    }
}
