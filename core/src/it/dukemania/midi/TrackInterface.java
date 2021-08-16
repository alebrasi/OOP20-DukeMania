package it.dukemania.midi;

import java.util.List;

public interface TrackInterface {

    List<AbstractNote> getNotes();

    int getChannel();

    void deleteNote(AbstractNote note);

}