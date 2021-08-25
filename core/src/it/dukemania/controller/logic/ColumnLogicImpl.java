package it.dukemania.controller.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import it.dukemania.audioengine.PlayerAudio;
import it.dukemania.midi.AbstractNote;
import it.dukemania.midi.ParsedTrack;
import it.dukemania.midi.Song;
import it.dukemania.util.Pair;

public class ColumnLogicImpl implements ColumnLogic {

    private static final int COLUMN_MAX_CAP = 8;
    private static final int COLUMN_MIN_CAP = 4;
    private static final int MAX_HEIGHT = 4;
    private int columnNumber;
    private List<NoteRange> noteRanges;
    private PlayerAudio player;
    private ScoreContext context;

    public ColumnLogicImpl(final int columnNumber) {
        setColumnNumber(columnNumber);
        this.noteRanges = new ArrayList<>();
        contextInit();
    }

    @Override
    public final int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public final void setColumnNumber(final int columnNumber) {
        this.columnNumber = columnNumber <= COLUMN_MAX_CAP && columnNumber >= COLUMN_MIN_CAP ? columnNumber : COLUMN_MIN_CAP;
    }

    @Override
    public final void addNoteRanges(final Columns column, final long start, final long end) {
        this.noteRanges.add(new NoteRange(column, start, end));
    }

    @Override
    public final void initAudio(final Song song) {
        player = new PlayerAudio(song);
        Runnable midiPlayer = () -> {
            while (!player.playNotes()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(midiPlayer).start();
    }

    @Override
    public final void play() {

    }

    private List<AbstractNote> overlappingNotes(final List<AbstractNote> notes) {
        return notes.stream().collect(Collectors.toMap(x -> x, x -> notes.stream()
                        .filter(y -> x != y && (x.getStartTime() == y.getStartTime()
                        || (x.getStartTime() < y.getStartTime() && (x.getStartTime() + x.getDuration().get()
                                        > y.getStartTime())))).collect(Collectors.toList()))).entrySet().stream()
                        .filter(x -> x.getValue().size() != 0)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList()); //list of all the notes that need to be removed
    }

    private List<Columns> getColumnList() {
        final List<Columns> columnList = Arrays.stream(Columns.values()).collect(Collectors.toList());
        columnList.sort(Comparator.comparing(Columns::getNumericValue));
        return columnList;
    }

    private List<List<AbstractNote>> generateNoteRanges(final List<List<AbstractNote>> list) {
        // used to verify the pressed notes during gameplay
        final List<Columns> columnList = getColumnList();
        noteRanges = list.stream().map(t -> {
                final Columns column = columnList.remove(0);
                return t.stream().map(r -> {
                        return new NoteRange(column, r.getStartTime(), r.getStartTime()
                        + r.getDuration().get().intValue());
                }).collect(Collectors.toList());
        }).collect(Collectors.toList()).stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return list;
    }

    private Optional<Long> getMaxDuration(final ParsedTrack track) {
        //return the max duration of a note in a track
        return track.getNotes().stream()
                .max(Comparator.comparing(e -> e.getDuration().get()))
                .get()
                .getDuration();
    }

    public static final int generateNoteHeight(final Optional<Long> noteDuration, final Optional<Long> maxDuration) {
      //return an int between 1 and 4 based on the duration of the note and the max duration of a note in the current track
        return IntStream.iterate(1, i -> i + 1)
        .limit(MAX_HEIGHT)
        .filter(x -> noteDuration.orElse(0L) <= maxDuration.orElse(0L) / MAX_HEIGHT * x)
        .findFirst()
        .orElse(1);
    }

    @Override
    public final List<LogicNote> noteQueuing(final ParsedTrack track) {
        List<Pair<Integer, List<AbstractNote>>> notesForNoteType = track.getNotes().stream()
                .collect(Collectors.groupingBy(
                        AbstractNote::getIdentifier, Collectors.toList()))
                .entrySet()
                .stream()
                .map(x -> new Pair<Integer, List<AbstractNote>>(x.getKey(), x.getValue()))
                .collect(Collectors.toList()); // notes grouped by identifier

        while (notesForNoteType.size() > columnNumber) { //until the groups of notes are less than the number of columns
            notesForNoteType.sort(Comparator.comparingInt(e -> e.getY().size())); //smaller groups first
            notesForNoteType.set(0, new Pair<Integer, List<AbstractNote>>(notesForNoteType.get(0).getX(),
                    Stream.concat(notesForNoteType.get(0).getY().stream(), notesForNoteType.remove(1).getY().stream())
                    .collect(Collectors.toList()))); //add the first 2 groups in the first removing the second
        }

        final List<Columns> columnList = getColumnList();
        return (generateNoteRanges(notesForNoteType.stream()
                .map(x -> x.getY())
                .peek(x -> x.removeAll(overlappingNotes(x))) // remove all overlapping notes
                .collect(Collectors.toList()))) // create the ranges for the gameplay
                .stream()
                .map(x -> {
                    final Columns currentColumn = columnList.remove(0);
                    return x.stream()
                            .map(y -> 
                            new LogicNoteImpl(y, currentColumn,
                                    generateNoteHeight(y.getDuration(), getMaxDuration(track))))
                            .collect(Collectors.toList());
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public final void contextInit() {
        this.context = new ScoreContext(new FullCalculator());
    }

    @Override
    public final int verifyNote(final Columns column, final long start, final long end) {
        NoteRange currentRange = noteRanges.stream()
                .filter(x -> x.getColumn().equals(column))
                .filter(x -> start < x.getEnd() && end > x.getStart())
                //the player started pressing before the end of the note and ended pressing after the start of the note
                .sorted(Comparator.comparingLong(NoteRange::getStart))
                .findFirst() //take the first range of the compatible with the pressed note
                .orElse(new NoteRange(column, 0, 1));
        this.noteRanges.remove(currentRange);
        return this.context.executeStrategy(column, start, end, currentRange, columnNumber);
    }

}
