package it.dukemania.model.serializers.synthesizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.dukemania.audioengine.Enveloper;
import it.dukemania.audioengine.LFOFactory;
import it.dukemania.audioengine.SynthBuilderImpl;
import it.dukemania.audioengine.WaveTable;

import java.io.IOException;
import java.util.function.Function;

public class SynthBuilderDeserializer extends StdDeserializer<SynthBuilderImpl> {

    /**
     * 
     */
    private static final long serialVersionUID = -1025096348936099734L;

    public SynthBuilderDeserializer() {
        this(null);
    }

    public SynthBuilderDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public final SynthBuilderImpl deserialize(final JsonParser p, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        SynthBuilderImpl synth = new SynthBuilderImpl();

        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        ObjectMapper mapper = new ObjectMapper();

        Enveloper env = mapper
                            .treeAsTokens(node.get("enveloper"))
                            .readValueAs(Enveloper.class);

        WaveTable[] wavetables = mapper.treeAsTokens(node.get("wavetables")).readValueAs(WaveTable[].class);
        double[] offsets = mapper.treeAsTokens(node.get("offsets")).readValueAs(double[].class);

        JsonNode noteLfoNode = node.get("noteLFO");
        JsonNode volumeLfoNode = node.get("volumeLFO");
        synth.setEnveloper(env);
        synth.setOffsets(offsets);
        synth.setWavetables(wavetables);
        synth.setNoteLFO(getLfoFunction(noteLfoNode, mapper));
        synth.setVolumeLFO(getLfoFunction(volumeLfoNode, mapper));

        return synth;
    }

    private Function<Long, Float> getLfoFunction(final JsonNode lfoNode, final ObjectMapper mapper) throws IOException {
        if (lfoNode != null) {
            LFOFactory.Types lfoType = mapper.treeAsTokens(lfoNode.get("type")).readValueAs(LFOFactory.Types.class);
            float[] lfoArgs = mapper.treeAsTokens(lfoNode.get("arguments")).readValueAs(float[].class);
            int duration = lfoNode.get("duration").asInt();
            return LFOFactory.general(lfoType, lfoArgs, duration);
        }
        return null;
    }

}
