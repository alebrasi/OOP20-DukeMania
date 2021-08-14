package it.dukemania.logic;

import java.util.List;
import java.util.stream.Collectors;

import it.dukemania.midi.Note;
import it.dukemania.midi.MyTrack;
import it.dukemania.midi.Song;



public class TrackFilterImpl implements TrackFilter {

    static final int MAX_NOTE = 600;

    @Override
    public final List<MyTrack> reduceTrack(final Song song) {
        return song.getTracks().stream().map(x -> {
           int numberOfNotes = x.getNotes().size();
           List<Note> notePos = x.getNotes();
           return new MyTrack(x.getInstrument(), x.getNotes().stream()
                   .filter(y -> (notePos.indexOf(y) % Math.ceil((double) numberOfNotes / MAX_NOTE) == 0))
                   .collect(Collectors.toList()), x.getChannel());
        }).collect(Collectors.toList());
    }

}
