package it.dukemania.midi;

import java.util.Collection;

public class MyTrack {

    private final Enum<InstrumentType> instrument;
    private final Collection<Note> notes;
    private final int channel;

    public MyTrack(final Enum<InstrumentType> instrument, final Collection<Note> notes, final int channel) {
        this.instrument = instrument;
        this.notes = notes;
        this.channel = channel;
    }
    public final Enum<InstrumentType> getInstrument() {
        return instrument;
    }
    public final Collection<Note> getNotes() {
        return notes;
    }
    public final int getChannel() {
        return channel;
    }

}
