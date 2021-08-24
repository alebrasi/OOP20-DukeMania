package it.dukemania.Model.serializers.song;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class TrackSerializer extends StdSerializer<TrackInfo> {

    /**
     * 
     */
    private static final long serialVersionUID = 9011388367001622959L;

    public TrackSerializer() {
        this(null);
    }

    public TrackSerializer(final Class<TrackInfo> t) {
        super(t);
    }

    @Override
    public void serialize(final TrackInfo value, final JsonGenerator gen, final SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("trackName", value.getTrackName());
        gen.writeObjectField("channel", value.getChannel());
        gen.writeObjectField("instrument", value.getInstrument());
        gen.writeObjectField("difficultyLevel", value.getDifficultyLevel().toString());
        gen.writeEndObject();
    }
}
