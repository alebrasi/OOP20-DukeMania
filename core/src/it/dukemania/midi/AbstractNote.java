package it.dukemania.midi;

import java.util.Optional;

public abstract class AbstractNote {
    private final Optional<Long> duration;
    private final long startTime;
    private final int identifier;

    //TODO guarda factory

    public AbstractNote(final Optional<Long> duration, final long startTime2, final int identifier) {
        this.duration = duration;
        this.startTime = startTime2;
        this.identifier = identifier;
    }

    public abstract Object  getItem();

    public final Optional<Long> getDuration() {
        return duration;
    }

    public final long getStartTime() {
        return startTime;
    }

    public final int getIdentifier() {
        return identifier;
    }

}
