package it.dukemania.model.serializers.leaderboard;

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
import java.util.Map;

public class SongLeaderBoardDeserializer extends StdDeserializer<SongLeaderBoard> {

    /**
     * 
     */
    private static final long serialVersionUID = 3313707952470700797L;

    public SongLeaderBoardDeserializer() {
        this(null);
    }

    public SongLeaderBoardDeserializer(final Class<SongLeaderBoard> t) {
        super(t);
    }

    @Override
    public final SongLeaderBoard deserialize(final JsonParser p, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode scoresNode = node.get("scores");

        JavaType mapLeaderBoardType = mapper.constructType(new TypeReference<Map<String, Integer>>() {
        });

        Map<String, Integer> usersScore = mapper.readValue(mapper.treeAsTokens(scoresNode), mapLeaderBoardType);

        return new SongLeaderBoard(node.get("songHash").asText(), usersScore);
    }
}
