package it.dukemania.midi;

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

    public final void deleteNote(final Note note) {
        this.notes.remove(note);
    }

    private Map<Integer, Double> calcMaxDuration() {
        return notes.stream()
                    .collect(Collectors.toMap(Note::getIdentifier, n -> n.getDuration().get(), (d1, d2) -> d1 > d2 ? d1 : d2));
    }

}
