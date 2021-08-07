package it.dukemania.Model;

import java.util.Optional;

public class Note {
    private final Optional<Double> duration;
    private final int startTime;
    private final int identifier;


    public Note(final Optional<Double> duration, final int startTime, final int identifier) {
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
