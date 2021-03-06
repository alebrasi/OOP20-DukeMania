package it.dukemania.midi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class KeyboardTrack extends ParsedTrack {
    private Enum<InstrumentType> instrument;
    private final Map<Integer, Long> notesMaxDuration;

    /**
     * this is the constructor.
     * @param instrument the instrument associated to this track
     * @param notes the list of note that compose the track
     * @param channel the MIDI channel associated to this track
     */
    public KeyboardTrack(final Enum<InstrumentType> instrument, final List<AbstractNote> notes, final int channel) {
        super(notes, channel);
        this.instrument = instrument;
        this.notesMaxDuration = calcMaxDuration();
    }

    /**
     * this method return the instrument associated to this track.
     * @return the instrument in which this track is played
     */
    public final Enum<InstrumentType> getInstrument() {
        return instrument;
    }

    /**
     * this method set the instrument associated to this track.
     * @param inst the element of the enum InstrumentType to associate to the track
     */
    public final void setInstrument(final InstrumentType inst) {
        instrument = inst;
    }

    /**
     * this method returns the map that associate each note that compose the track to its max duration.
     * @return a map associating the identifier of the note and its duration
     */
    public final Map<Integer, Long> getNotesMaxDuration() {
        return notesMaxDuration;
    }

    /**
     * this method calculate the map that associate each note that compose the track to its max duration.
     * @return a map associating the identifier of the note and its duration
     */
    private Map<Integer, Long> calcMaxDuration() {
        return super.getNotes().stream()
                                    .collect(Collectors.toMap(AbstractNote::getIdentifier,
                                            n -> n.getDuration().orElse(0L), (d1, d2) -> d1 > d2 ? d1 : d2));
    }

}
