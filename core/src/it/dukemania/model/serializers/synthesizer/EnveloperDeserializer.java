package it.dukemania.model.serializers.synthesizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.dukemania.audioengine.Enveloper;

import java.io.IOException;

public class EnveloperDeserializer extends StdDeserializer<Enveloper> {

    /**
     * 
     */
    private static final long serialVersionUID = 466673112602165533L;

    public EnveloperDeserializer() {
        this(null);
    }

    public EnveloperDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public final Enveloper deserialize(final JsonParser p, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        return new Enveloper(node.get("attackMS").asLong(), node.get("attackVolume").asLong(), node.get("releaseMS").asLong());
    }
}
