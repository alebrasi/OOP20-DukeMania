package it.dukemania.logic;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import it.dukemania.midi.GenericNote;
import it.dukemania.midi.MyTrack;
import it.dukemania.midi.Note;
import it.dukemania.midi.Song;


public class TestLogic {

    private TrackFilterImpl trackFilter;
    private GameUtilities gameUtilities;
    private ColumnLogic columnLogic;

    private List<Note> createNotes(final int quantity) { 
        List<Note> testNotes = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            testNotes.add(new GenericNote(Optional.of(2.0), i, 0));
        }
        return testNotes;
    }


    @org.junit.Before
    public void init() {
            this.trackFilter = new TrackFilterImpl();
            this.gameUtilities = new GameUtilitiesImpl();
            this.columnLogic = new ColumnLogicImpl(4);
    }


    @org.junit.Test
    public void testTrackFilter() {
        Set<MyTrack> testTracks = new HashSet<>();
        List<Note> testNotes = createNotes(TrackFilterImpl.MAX_NOTE);
        testTracks.add(new MyTrack(null, testNotes, 0));
        assertTrue(this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0)).size() == 1);
        assertTrue(this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0)).stream()
                .collect(Collectors.toList()).get(0).getNotes().size() == TrackFilterImpl.MAX_NOTE);

        testNotes.add(new GenericNote(Optional.of(1.0), 1, 1));
        assertTrue(this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0)).stream()
                .collect(Collectors.toList()).get(0).getNotes().size() <= TrackFilterImpl.MAX_NOTE);

        testTracks.add(new MyTrack(null, testNotes.subList(0, 10), 0)); 
        List<MyTrack> filteredTracks = this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0));
        assertTrue(filteredTracks.get(0).getNotes().size() == 10);
        assertTrue(filteredTracks.get(1).getNotes().size() <= TrackFilterImpl.MAX_NOTE);

    }

    @org.junit.Test
    public void testGameUtilities() {
        int difficulties = DifficultyLevel.values().length - 1;
        List<MyTrack> testTracksDiff = new ArrayList<>();
        testTracksDiff.add(new MyTrack(null,
                createNotes(TrackFilterImpl.MAX_NOTE / difficulties * DifficultyLevel.VERY_EASY.getNumericValue()), 0));
        testTracksDiff.add(new MyTrack(null,
                createNotes(TrackFilterImpl.MAX_NOTE / difficulties * DifficultyLevel.EASY.getNumericValue()), 0));
        testTracksDiff.add(new MyTrack(null,
                createNotes(TrackFilterImpl.MAX_NOTE / difficulties * DifficultyLevel.NORMAL.getNumericValue()), 0));
        testTracksDiff.add(new MyTrack(null,
                createNotes(TrackFilterImpl.MAX_NOTE / difficulties * DifficultyLevel.DIFFICULT.getNumericValue()), 0));
        testTracksDiff.add(new MyTrack(null,
                createNotes(TrackFilterImpl.MAX_NOTE / difficulties * DifficultyLevel.VERY_DIFFICULT.getNumericValue()), 0));
        testTracksDiff.add(new MyTrack(null, createNotes(TrackFilterImpl.MAX_NOTE + 1), 0));
        Map<MyTrack, DifficultyLevel> trackmap = this.gameUtilities.setTracksDifficulty(testTracksDiff);
        assertEquals(trackmap.get(testTracksDiff.get(0)), DifficultyLevel.VERY_EASY);
        assertEquals(trackmap.get(testTracksDiff.get(1)), DifficultyLevel.EASY);
        assertEquals(trackmap.get(testTracksDiff.get(2)), DifficultyLevel.NORMAL);
        assertEquals(trackmap.get(testTracksDiff.get(3)), DifficultyLevel.DIFFICULT);
        assertEquals(trackmap.get(testTracksDiff.get(4)), DifficultyLevel.VERY_DIFFICULT);
        assertEquals(trackmap.get(testTracksDiff.get(5)), DifficultyLevel.UNKNOWN);
    }

    @org.junit.Test
    public void testColumnLogic() {
        List<Note> testNotes = new ArrayList<>();
        testNotes.add(new GenericNote(Optional.of(2.0), 1, 0));
        MyTrack testTrack = new MyTrack(null, testNotes, 0);
        Map<ColumnsEnum, List<Note>> queuedNotes = this.columnLogic.noteQueuing(testTrack);
        assertTrue(queuedNotes.size() == 1);
        assertTrue(queuedNotes.get(ColumnsEnum.COLUMN_1).size() == 1);

        testNotes.add(new GenericNote(Optional.of(2.0), 10, 1));
        testNotes.add(new GenericNote(Optional.of(2.0), 20, 2));
        testNotes.add(new GenericNote(Optional.of(2.0), 30, 3));
        testNotes.add(new GenericNote(Optional.of(2.0), 40, 4));
        testTrack = new MyTrack(null, testNotes, 0);
        queuedNotes = this.columnLogic.noteQueuing(testTrack);
        assertTrue(queuedNotes.size() == 4);
        assertTrue(queuedNotes.values().stream().mapToInt(t -> t.size()).sum() == 5);
        this.columnLogic.setColumnNumber(5);
        queuedNotes = this.columnLogic.noteQueuing(testTrack);
        assertTrue(queuedNotes.size() == 5);

        testNotes.addAll(createNotes(10));
        queuedNotes = this.columnLogic.noteQueuing(testTrack);
        assertTrue(queuedNotes.values().stream().mapToInt(t -> t.size()).sum() == 5);
    }

}
