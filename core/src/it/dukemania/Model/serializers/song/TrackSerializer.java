package it.dukemania.Model.serializers.song;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class TrackSerializer extends StdSerializer<TrackInfo> {

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
        gen.writeEndObject();
    }
}
