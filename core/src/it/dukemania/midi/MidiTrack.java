package it.dukemania.midi;

import java.util.List;

public interface MidiTrack {

    List<AbstractNote> getNotes();

    int getChannel();

    void deleteNote(AbstractNote note);

}
