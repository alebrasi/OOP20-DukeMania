package it.dukemania.Controller.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import it.dukemania.midi.AbstractNote;
import it.dukemania.midi.FactoryConfigurator;
import it.dukemania.midi.MidiTrack;
import it.dukemania.midi.Note;
import it.dukemania.midi.Song;


public class TestLogic {

    private TrackFilterImpl trackFilter;
    private GameUtilities gameUtilities;
    private ColumnLogic columnLogic;

    private List<AbstractNote> createNotes(final int quantity) { 
        List<AbstractNote> testNotes = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            testNotes.add(new Note(Optional.of(2L), i, 0));
        }
        return testNotes;
    }

    private List<List<LogicNote>> logicNoteGrouping(final List<LogicNote> notes) {
        return notes.stream()
                .collect(Collectors.groupingBy(LogicNote::getColumn, Collectors.toList()))
                .values()
                .stream()
                .sorted(Comparator.comparing(e -> e.size()))
                .collect(Collectors.toList());
    }

    @org.junit.Before
    public void init() {
            this.trackFilter = new TrackFilterImpl();
            this.gameUtilities = new GameUtilitiesImpl();
            this.columnLogic = new ColumnLogicImpl(4);
    }


    @org.junit.Test
    public void testTrackFilter() {
        List<MidiTrack> testTracks = new ArrayList<>();
        List<AbstractNote> testNotes = createNotes(TrackFilterImpl.MAX_NOTE);
        testTracks.add(FactoryConfigurator.getFactory(1).createTrack(null, testNotes, 0));

        //test with MAX_NOTE notes
        assertTrue(this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0)).size() == 1);
        assertTrue(this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0)).stream()
                .collect(Collectors.toList()).get(0).getNotes().size() == TrackFilterImpl.MAX_NOTE);

        //test with MAX_NOTE + 1 notes
        testNotes.add(FactoryConfigurator.getFactory(1).createNote(Optional.of(1L), 1, 1));
        assertTrue(this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0)).stream()
                .collect(Collectors.toList()).get(0).getNotes().size() <= TrackFilterImpl.MAX_NOTE);

        //test with 2 tracks (10 and MAX_NOTE + 1 notes)
        testTracks.add(FactoryConfigurator.getFactory(1).createTrack(null, testNotes.subList(0, 10), 0)); 
        List<MidiTrack> filteredTracks = this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0));
        assertTrue(filteredTracks.get(0).getNotes().size() == 10);
        assertTrue(filteredTracks.get(1).getNotes().size() <= TrackFilterImpl.MAX_NOTE);

        //test with MAX_NOTE * 2 + 1 notes
        testTracks.clear();
        testNotes = createNotes(TrackFilterImpl.MAX_NOTE * 2 + 1);
        testTracks.add(FactoryConfigurator.getFactory(1).createTrack(null, testNotes, 0));
        assertTrue(this.trackFilter.reduceTrack(new Song("title", 0, testTracks, 0)).stream()
                .collect(Collectors.toList()).get(0).getNotes().size() <= TrackFilterImpl.MAX_NOTE);
    }

    @org.junit.Test
    public void testGameUtilities() {
        int difficulties = DifficultyLevel.values().length - 1;
        List<MidiTrack> testTracksDiff = new ArrayList<>();

        //test for every difficultylevel
        testTracksDiff.add(FactoryConfigurator.getFactory(1).createTrack(null,
                createNotes(TrackFilterImpl.MAX_NOTE / difficulties * DifficultyLevel.VERY_EASY.getNumericValue()), 0));
        testTracksDiff.add(FactoryConfigurator.getFactory(1).createTrack(null,
                createNotes(TrackFilterImpl.MAX_NOTE / difficulties * DifficultyLevel.EASY.getNumericValue()), 0));
        testTracksDiff.add(FactoryConfigurator.getFactory(1).createTrack(null,
                createNotes(TrackFilterImpl.MAX_NOTE / difficulties * DifficultyLevel.NORMAL.getNumericValue()), 0));
        testTracksDiff.add(FactoryConfigurator.getFactory(1).createTrack(null,
                createNotes(TrackFilterImpl.MAX_NOTE / difficulties * DifficultyLevel.DIFFICULT.getNumericValue()), 0));
        testTracksDiff.add(FactoryConfigurator.getFactory(1).createTrack(null,
                createNotes(TrackFilterImpl.MAX_NOTE / difficulties * DifficultyLevel.VERY_DIFFICULT.getNumericValue()), 0));
        //test special case: track with more notes than MAX_NOTE
        testTracksDiff.add(FactoryConfigurator.getFactory(1).createTrack(null, createNotes(TrackFilterImpl.MAX_NOTE + 1), 0));
        Map<MidiTrack, DifficultyLevel> trackmap = this.gameUtilities.generateTracksDifficulty(testTracksDiff);
        assertEquals(trackmap.get(testTracksDiff.get(0)), DifficultyLevel.VERY_EASY);
        assertEquals(trackmap.get(testTracksDiff.get(1)), DifficultyLevel.EASY);
        assertEquals(trackmap.get(testTracksDiff.get(2)), DifficultyLevel.NORMAL);
        assertEquals(trackmap.get(testTracksDiff.get(3)), DifficultyLevel.DIFFICULT);
        assertEquals(trackmap.get(testTracksDiff.get(4)), DifficultyLevel.VERY_DIFFICULT);
        assertEquals(trackmap.get(testTracksDiff.get(5)), DifficultyLevel.UNKNOWN);
    }

    @org.junit.Test
    public void testColumnLogic() {
        List<AbstractNote> testNotes = new ArrayList<>();

        //test with only a note
        testNotes.add(FactoryConfigurator.getFactory(1).createNote(Optional.of(2L), 1, 0));
        MidiTrack testTrack = FactoryConfigurator.getFactory(1).createTrack(null, testNotes, 0);
        List<List<LogicNote>> queuedNotes = logicNoteGrouping(this.columnLogic.noteQueuing(testTrack));
        assertTrue(queuedNotes.size() == 1);
        assertTrue(queuedNotes.get(0).size() == 1);

        //test with 5 notes with different identifier and 4 columns
        testNotes.add(FactoryConfigurator.getFactory(1).createNote(Optional.of(1L), 10, 1));
        testNotes.add(FactoryConfigurator.getFactory(1).createNote(Optional.of(2L), 20, 2));
        testNotes.add(FactoryConfigurator.getFactory(1).createNote(Optional.of(3L), 30, 3));
        testNotes.add(FactoryConfigurator.getFactory(1).createNote(Optional.of(4L), 40, 4));
        testTrack = FactoryConfigurator.getFactory(1).createTrack(null, testNotes, 0);
        queuedNotes = logicNoteGrouping(this.columnLogic.noteQueuing(testTrack));
        assertTrue(queuedNotes.size() == 4);
        assertTrue(queuedNotes.stream().mapToInt(t -> t.size()).sum() == 5);

        //test with 5 notes with different identifier and 5 columns
        this.columnLogic.setColumnNumber(5);
        queuedNotes = logicNoteGrouping(this.columnLogic.noteQueuing(testTrack));
        assertTrue(queuedNotes.size() == 5);

        //test with multiple overlapped notes
        testNotes.addAll(createNotes(10));
        queuedNotes = logicNoteGrouping(this.columnLogic.noteQueuing(testTrack));
        assertTrue(queuedNotes.stream().mapToInt(t -> t.size()).sum() == 5);
    }

    @org.junit.Test
    public void testScore() {
        this.columnLogic.addNoteRanges(Columns.COLUMN_1, 10, 15);
        this.columnLogic.addNoteRanges(Columns.COLUMN_1, 16, 20);
        this.columnLogic.addNoteRanges(Columns.COLUMN_2, 11, 16);
        this.columnLogic.addNoteRanges(Columns.COLUMN_2, 21, 25);
        this.columnLogic.addNoteRanges(Columns.COLUMN_2, 30, 40);
        this.columnLogic.addNoteRanges(Columns.COLUMN_2, 50, 70);
        this.columnLogic.addNoteRanges(Columns.COLUMN_3, 10, 30);
 
        //test ranges with the same note
        assertTrue(this.columnLogic.verifyNote(Columns.COLUMN_1, 9, 15) > 0); 
        // match with first added range, partial match
        assertTrue(this.columnLogic.verifyNote(Columns.COLUMN_1, 9, 15) == 0); 
        // match with second added range, complete mismatch
        assertTrue(this.columnLogic.verifyNote(Columns.COLUMN_1, 9, 15) == 0); 
        //match with orElse value, complete mismatch

        //test perfect note and combo 
        assertTrue(this.columnLogic.verifyNote(Columns.COLUMN_2, 11, 16) == (100 + 1 * 5) * 4); 
        //perfect socre, 1 combo, 4 colums
        assertTrue(this.columnLogic.verifyNote(Columns.COLUMN_2, 21, 25) == (100 + 2 * 5) * 4); 
        //perfect socre, 2 combo, 4 colums
        this.columnLogic.setColumnNumber(8);
        assertTrue(this.columnLogic.verifyNote(Columns.COLUMN_2, 30, 40) == (100 + 3 * 5 ) * 8); 
        //perfect socre, 3 combo, 8 colums
        assertTrue(this.columnLogic.verifyNote(Columns.COLUMN_2, 50, 60) != (100 + 4 * 5 ) * 8); 
        //not perfect note, reset combo

        //test tolerance
        assertTrue(this.columnLogic.verifyNote(Columns.COLUMN_3, 10, 29) == (100 + 1 * 5) * 8); 
        //not perfect but considered as perfect
    }

}
