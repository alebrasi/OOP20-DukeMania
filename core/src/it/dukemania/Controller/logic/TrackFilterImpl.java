package it.dukemania.Controller.logic;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import it.dukemania.midi.AbstractNote;
import it.dukemania.midi.FactoryConfigurator;
import it.dukemania.midi.ParsedTrack;
import it.dukemania.midi.Song;
import it.dukemania.midi.TrackImpl;

public class TrackFilterImpl implements TrackFilter {

    static final int MAX_NOTE = 600;
    static final int PERCUSSION_CHANNEL = 10;
    static final long MIN_DURATION = 125000; //microseconds

    @Override
    public final List<ParsedTrack> reduceTrack(final Song song) {
        return song.getTracks().stream()
                .filter(x -> x.getChannel() != PERCUSSION_CHANNEL)//remove unplayable tracks
                .map(x -> FactoryConfigurator.getFactory(x.getChannel()).createTrack(((TrackImpl) x).getInstrument(),
                        x.getNotes().stream()
                        .filter(y -> y.getDuration().orElse(0L) >= MIN_DURATION)
                        //remove unplayable notes
                        .collect(Collectors.toList()), x.getChannel()))
                .map(x -> {
                    final int numberOfNotes = x.getNotes().size();
                    final List<AbstractNote> notePos = x.getNotes();
                    return FactoryConfigurator.getFactory(x.getChannel()).createTrack(((TrackImpl) x).getInstrument(),
                            x.getNotes().stream()
                            .filter(y -> notePos.indexOf(y) % Math.ceil((double) numberOfNotes / MAX_NOTE) == 0) 
                            //remove extra notes
                            .collect(Collectors.toList()), x.getChannel());
                    })
                .sorted(Comparator.comparing(e -> e.getNotes().size()))
                .collect(Collectors.toList());
    }

}
