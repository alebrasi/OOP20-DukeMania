package it.dukemania.midi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class TrackImpl extends PercussionTrack {

    private final Enum<InstrumentType> instrument;
    private final Map<Integer, Double> notesMaxDuration;

    public TrackImpl(final Enum<InstrumentType> instrument, final List<AbstractNote> notes, final int channel) {
        super(notes, channel);
        this.instrument = instrument;
        this.notesMaxDuration = calcMaxDuration();
    }

    public final Enum<InstrumentType> getInstrument() {
        return instrument;
    }
    public final Map<Integer, Double> getNotesMaxDuration() {
        return notesMaxDuration;
    }

    private Map<Integer, Double> calcMaxDuration() {
        return notes.stream()
                    .collect(Collectors.toMap(AbstractNote::getIdentifier,
                            n -> n.getDuration().get(), (d1, d2) -> d1 > d2 ? d1 : d2));
    }

}
