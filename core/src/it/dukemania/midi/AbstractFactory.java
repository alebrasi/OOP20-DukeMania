package it.dukemania.midi;

import java.util.List;
import java.util.Optional;

public interface AbstractFactory {

    /**
     * this method return a note.
     * @param duration
     * @param startTime
     * @param identifier
     * @return an abstract note
     */
    AbstractNote createNote(Optional<Long> duration, long startTime, int identifier);

    /**
     * this method return a track.
     * @param instrument
     * @param notes
     * @param channel
     * @return a midi track
     */
    MidiTrack createTrack(Enum<InstrumentType> instrument, List<AbstractNote> notes, int channel);

}
