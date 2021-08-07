package it.dukemania.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dukemania.Model.InstrumentType;
import it.dukemania.Model.Note;
import it.dukemania.Model.serializers.TrackDeserializer;
import it.dukemania.Model.serializers.TrackSerializer;

import java.util.Collection;

@JsonDeserialize(using = TrackDeserializer.class)
@JsonSerialize(using = TrackSerializer.class)
public class MyTrack {
    private final Enum<InstrumentType> instrument;
    private final Collection<Note> notes;
    private final int channel;
    private final String name;

    public MyTrack(final String name, final Enum<InstrumentType> instrument, final Collection<Note> notes, final int channel) {
        this.instrument = instrument;
        this.name = name;
        this.notes = notes;
        this.channel = channel;
    }
    public final Enum<InstrumentType> getInstrument() {
        return instrument;
    }

    public final Collection<Note> getNotes() {
        return notes;
    }

    public final int getChannel() {
        return channel;
    }

    public final String getName() {
        return name;
    }
}
