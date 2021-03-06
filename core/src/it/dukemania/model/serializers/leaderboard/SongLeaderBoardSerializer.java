package it.dukemania.model.serializers.leaderboard;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SongLeaderBoardSerializer extends StdSerializer<SongLeaderBoard> {

    /**
     * 
     */
    private static final long serialVersionUID = 6106135137007156342L;

    public SongLeaderBoardSerializer() {
        this(null);
    }

    protected SongLeaderBoardSerializer(final Class<SongLeaderBoard> t) {
        super(t);
    }

    @Override
    public final void serialize(final SongLeaderBoard value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("songHash", value.getSongHash());
        gen.writeObjectField("scores", value.getUsersScore());
        gen.writeEndObject();
    }
}
