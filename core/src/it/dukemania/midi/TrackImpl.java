package it.dukemania.midi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class TrackImpl extends PercussionTrackImpl {

    private final Enum<InstrumentType> instrument;
    private final Map<Double, Long> notesMaxDuration;

    public TrackImpl(final Enum<InstrumentType> instrument, final List<AbstractNote> notes, final int channel) {
        super(notes, channel);
        this.instrument = instrument;
        this.notesMaxDuration = calcMaxDuration();
    }

    public final Enum<InstrumentType> getInstrument() {
        return instrument;
    }
    public final Map<Double, Long> getNotesMaxDuration() {
        return notesMaxDuration;
    }

    private Map<Double, Long> calcMaxDuration() {
        return super.getNotes().stream()
                                .map(n -> (Note) n)
                                .collect(Collectors.toMap(Note::getFrequency,
                                        n -> n.getDuration().get(), (d1, d2) -> d1 > d2 ? d1 : d2));
    }

}
