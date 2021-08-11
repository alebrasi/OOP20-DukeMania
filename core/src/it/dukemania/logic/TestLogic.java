package it.dukemania.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import it.dukemania.midi.GenericNote;
import it.dukemania.midi.MyTrack;
import it.dukemania.midi.Note;
import it.dukemania.midi.Song;


public class TestLogic {

    private TrackFilter trackFilter;
    private GameUtilities gameUtilities;

    @org.junit.Before
    public void init() {
            this.trackFilter = new TrackFilterImpl();
            this.gameUtilities = new GameUtilitiesImpl();
    }


    @org.junit.Test
    public void testTrackFilter() {
        List<Note> testNotes = new ArrayList<>();
        for (int i = 0; i < TrackFilter.MAX_NOTE; i++) {
            testNotes.add(new GenericNote(Optional.of(1.0), i, i));
        }
        
        Set<MyTrack> testTracks = new HashSet<>();
        testTracks.add(new MyTrack(null, testNotes, 0));
        assertTrue(this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0)).size() == 1);
        assertTrue(this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0)).stream()
                .collect(Collectors.toList()).get(0).getNotes().size() == TrackFilter.MAX_NOTE);
        
        testNotes.add(new GenericNote(Optional.of(1.0), 1, 1));
        assertTrue(this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0)).stream()
                .collect(Collectors.toList()).get(0).getNotes().size() <= TrackFilter.MAX_NOTE);
        
        testTracks.add(new MyTrack(null, testNotes.subList(0, 10), 0)); 
        List<MyTrack> filteredTracks = this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0)).stream()
        .collect(Collectors.toList());
        assertTrue(filteredTracks.get(0).getNotes().size() <= TrackFilter.MAX_NOTE);
        assertTrue(filteredTracks.get(1).getNotes().size() == 10);
    }

}
