package it.dukemania.Model.serializers.song;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.List;

public class SongDeserializer extends StdDeserializer<SongInfo> {

    /**
     * 
     */
    private static final long serialVersionUID = -5051112879681763904L;

    public SongDeserializer() {
        this(null);
    }

    public SongDeserializer(final Class<SongInfo> t) {
        super(t);
    }

    @Override
    public final SongInfo deserialize(final JsonParser p, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode trackNode = node.get("tracks");

        JavaType listTrackType = mapper.constructType(new TypeReference<List<TrackInfo>>() {
        });

        List<TrackInfo> tracks = mapper.readValue(mapper.treeAsTokens(trackNode), listTrackType);

        return new SongInfo(node.get("songName").asText(), node.get("fileHash").asText(), 
                node.get("duration").asDouble(), tracks, node.get("BPM").asDouble());
    }
}
