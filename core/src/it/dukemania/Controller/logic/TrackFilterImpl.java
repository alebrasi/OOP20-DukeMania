package it.dukemania.Controller.logic;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import it.dukemania.midi.AbstractNote;
import it.dukemania.midi.FactoryConfigurator;
import it.dukemania.midi.MidiTrack;
import it.dukemania.midi.Song;
import it.dukemania.midi.TrackImpl;



public class TrackFilterImpl implements TrackFilter {

    static final int MAX_NOTE = 600;

    @Override
    public final List<MidiTrack> reduceTrack(final Song song) {
        return song.getTracks().stream().filter(x -> x.getChannel() != 10).map(x -> {
           final int numberOfNotes = x.getNotes().size();
           final List<AbstractNote> notePos = x.getNotes();
           return FactoryConfigurator.getFactory(x.getChannel()).createTrack(((TrackImpl) x).getInstrument(),
                   x.getNotes().stream().filter(y -> notePos.indexOf(y) % Math.ceil((double) numberOfNotes / MAX_NOTE) == 0)
                   .collect(Collectors.toList()), x.getChannel());
        })
                .sorted(Comparator.comparing(e -> e.getNotes().size()))
                .collect(Collectors.toList());
    }

}
