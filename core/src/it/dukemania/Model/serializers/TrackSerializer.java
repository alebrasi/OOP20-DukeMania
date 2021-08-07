package it.dukemania.Model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.dukemania.Model.MyTrack;

import java.io.IOException;

public class TrackSerializer extends StdSerializer<MyTrack> {

    public TrackSerializer() {
        this(null);
    }

    public TrackSerializer(final Class<MyTrack> t) {
        super(t);
    }

    @Override
    public void serialize(final MyTrack value, final JsonGenerator gen, final SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("trackName", value.getName());
        gen.writeObjectField("channel", value.getChannel());
        gen.writeObjectField("instrument", value.getInstrument());
        gen.writeEndObject();
    }
}
