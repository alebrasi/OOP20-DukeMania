package it.dukemania.Model.serializers.synthesizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.dukemania.audioengine.SynthBuilderImpl;
import it.dukemania.midi.InstrumentType;

import java.io.IOException;
import java.util.List;

public class SynthesizerInfoDeserializer extends StdDeserializer<SynthInfo> {

    /**
     * 
     */
    private static final long serialVersionUID = 2612924517909551295L;

    public SynthesizerInfoDeserializer() {
        this(null);
    }

    public SynthesizerInfoDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public SynthInfo deserialize(final JsonParser p, final DeserializationContext ctxt) 
            throws IOException, JsonProcessingException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        ObjectMapper mapper = new ObjectMapper();

        SynthBuilderImpl synth = mapper
                                .treeAsTokens(node.get("values"))
                                .readValueAs(SynthBuilderImpl.class);

        JavaType listTrackType = mapper.constructType(new TypeReference<List<InstrumentType>>() {
        });

        List<InstrumentType> associatedInstruments = mapper.readValue(mapper
                                                                 .treeAsTokens(node.get("associatedInstruments")), listTrackType);

        return new SynthInfo(node.get("presetName").asText(), synth, associatedInstruments);
    }
}
