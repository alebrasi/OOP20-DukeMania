package it.dukemania.Model.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.dukemania.Model.InstrumentType;
import it.dukemania.Model.MyTrack;

import java.io.IOException;
import java.util.Collections;

public class TrackDeserializer extends StdDeserializer<MyTrack> {

    public TrackDeserializer() {
        this(null);
    }

    public TrackDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public MyTrack deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        return new MyTrack(node.get("trackName").asText(), InstrumentType.SLAP_BASS_1, Collections.EMPTY_LIST, node.get("channel").asInt());
    }
}