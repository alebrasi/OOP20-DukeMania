package it.dukemania.Model;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MyTrack {

    private final Enum<InstrumentType> instrument;
    private final List<Note> notes;
    private final int channel;
    private final Map<Integer, Double> notesMaxDuration;

    public MyTrack(final Enum<InstrumentType> instrument, final List<Note> notes, final int channel) {
        this.instrument = instrument;
        this.notes = notes;
        this.channel = channel;
        this.notesMaxDuration = calcMaxDuration();
    }

    public final Enum<InstrumentType> getInstrument() {
        return instrument;
    }
    public final List<Note> getNotes() {
        return notes;
    }
    public final int getChannel() {
        return channel;
    }

    public final Map<Integer, Double> getNotesMaxDuration() {
        return notesMaxDuration;
    }

    public final void deleteNotes(final List<Note> notes) {
        this.notes.removeAll(notes);
    }

    private Map<Integer, Double> calcMaxDuration() {
        return notes.stream()
                    .collect(Collectors.toMap(Note::getIdentifier, n -> n.getDuration().get(), (d1, d2) -> d1 > d2 ? d1 : d2));
    }

}

/*import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
*/


