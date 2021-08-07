package it.dukemania.Model.serializers;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.dukemania.Model.MyTrack;
import it.dukemania.Model.Song;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SongDeserializer extends StdDeserializer<Song> {

    public SongDeserializer() {
        this(null);
    }

    public SongDeserializer(final Class<Song> t) {
        super(t);
    }

    @Override
    public Song deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode trackNode = node.get("Tracks");

        JavaType listTrackType = mapper.constructType(new TypeReference<List<MyTrack>>() {
        });

        List<MyTrack> tracks = mapper.readValue(mapper.treeAsTokens(trackNode), listTrackType);

        return new Song(node.get("songName").asText(), node.get("duration").asDouble(), tracks, node.get("BPM").asDouble());
    }
}
