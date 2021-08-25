package it.dukemania.midi;

import java.util.List;
import java.util.Optional;

public interface AbstractFactory {

    /**
     * this method create a new note.
     * @param duration the duration of the note in microseconds
     * @param startTime the microsecond in which the note start
     * @param identifier the MIDI number that identifies the note
     * @return an abstract note
     */
    AbstractNote createNote(Optional<Long> duration, long startTime, int identifier);

    /**
     * this method create a new a track.
     * @param instrument the instrument associated with the track, this parameter is not used if the track is a PercussionTracks 
     * @param notes the list of notes that compose the track
     * @param channel the number of the MIDI channel associated to this track, can be considered an identifier for the track
     * @return a parsed track
     */
    ParsedTrack createTrack(Enum<InstrumentType> instrument, List<AbstractNote> notes, int channel);

}
