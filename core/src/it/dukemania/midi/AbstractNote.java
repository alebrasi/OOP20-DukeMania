package it.dukemania.midi;

import java.util.Optional;

public abstract class AbstractNote {
    private final Optional<Double> duration;
    private final int startTime;
    private final int identifier;

    //TODO guarda factory

    public AbstractNote(final Optional<Double> duration, final int startTime, final int identifier) {
        this.duration = duration;
        this.startTime = startTime;
        this.identifier = identifier;
    }

    public final Optional<Double> getDuration() {
        return duration;
    }

    public final int getStartTime() {
        return startTime;
    }

    public final int getIdentifier() {
        return identifier;
    }

}
