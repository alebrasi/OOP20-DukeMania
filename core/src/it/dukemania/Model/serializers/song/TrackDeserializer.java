package it.dukemania.Model.serializers.song;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.dukemania.midi.InstrumentType;

import java.io.IOException;

public class TrackDeserializer extends StdDeserializer<TrackInfo> {

    public TrackDeserializer() {
        this(null);
    }

    public TrackDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public TrackInfo deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        InstrumentType instrument = new ObjectMapper()
                                        .treeAsTokens(node.get("instrument"))
                                        .readValueAs(InstrumentType.class);

        return new TrackInfo(node.get("channel").asInt(), node.get("trackName").asText(), instrument);
    }
}
