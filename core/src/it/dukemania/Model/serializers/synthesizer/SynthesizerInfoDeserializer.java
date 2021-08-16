package it.dukemania.Model.serializers.synthesizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.dukemania.audioengine.Synth;
import it.dukemania.audioengine.SynthBuilder;
import it.dukemania.audioengine.SynthBuilderImpl;

import java.io.IOException;

public class SynthesizerInfoDeserializer extends StdDeserializer<SynthInfo> {

    public SynthesizerInfoDeserializer() {
        this(null);
    }

    public SynthesizerInfoDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public SynthInfo deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        SynthBuilderImpl synth = new ObjectMapper()
                                .treeAsTokens(node.get("values"))
                                .readValueAs(SynthBuilderImpl.class);

        return new SynthInfo(node.get("presetName").asText(), synth);
    }
}
