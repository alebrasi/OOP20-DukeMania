package it.dukemania.midi;

import java.util.Optional;

public abstract class AbstractNote {
    private final Optional<Long> duration;
    private final long startTime;
    private final int identifier;



    /**
     * this is a constructor.
     * @param duration
     * @param startTime2
     * @param identifier
     */
    public AbstractNote(final Optional<Long> duration, final long startTime2, final int identifier) {
        this.duration = duration;
        this.startTime = startTime2;
        this.identifier = identifier;
    }

    /**
     * this method depends on the type of the note and can return a drum sample (for PercussonNote)
     * or an integer representing the note identifier (for Note).
     * @return an object
     */
    public abstract Object  getItem();

    /**
     * this method return an optional describing the duration of the note in microseconds.
     * @return an optional duration
     */
    public final Optional<Long> getDuration() {
        return duration;
    }

    /**
     * this method return the note start time in microseconds.
     * @return the note start time
     */
    public final long getStartTime() {
        return startTime;
    }

    /**
     * this method return the MIDI identifier for the note.
     * @return the note identifier
     */
    public final int getIdentifier() {
        return identifier;
    }

}
