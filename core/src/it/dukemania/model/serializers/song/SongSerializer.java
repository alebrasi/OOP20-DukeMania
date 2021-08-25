package it.dukemania.model.serializers.song;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SongSerializer extends StdSerializer<SongInfo> {

    /**
     * 
     */
    private static final long serialVersionUID = 2497154748688506017L;

    public SongSerializer() {
        this(null);
    }

    public SongSerializer(final Class<SongInfo> t) {
        super(t);
    }

    @Override
    public final void serialize(final SongInfo value, final JsonGenerator gen, final SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("songName", value.getTitle());
        gen.writeObjectField("fileHash", value.getSongHash());
        gen.writeObjectField("duration", value.getDuration());
        gen.writeObjectField("BPM", value.getBPM());
        gen.writeObjectField("tracks", value.getTracks());
        gen.writeEndObject();
    }
}
