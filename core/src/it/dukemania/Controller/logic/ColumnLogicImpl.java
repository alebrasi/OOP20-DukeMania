package it.dukemania.Controller.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import it.dukemania.midi.AbstractNote;
import it.dukemania.midi.MidiTrack;

public class ColumnLogicImpl implements ColumnLogic {

    private static final int COLUMN_MAX_CAP = 8;
    private static final int COLUMN_MIN_CAP = 4;
    private static final int NOTE_POINT = 100;
    private static final int NOTE_TOLERANCE = 10;
    private static final int MAX_COMBO = 20;
    private static final int COMBO_POINT = 5;
    static final int MAX_HEIGHT = 4;
    private int columnNumber;
    private List<NoteRange> noteRanges;
    private int combo;

    public ColumnLogicImpl(final int columnNumber) {
        this.columnNumber = (columnNumber <= COLUMN_MAX_CAP && columnNumber >= COLUMN_MIN_CAP)
                        ? columnNumber : COLUMN_MIN_CAP;
        combo = 0;
    }

    @Override
    public final int getColumnNumber() {
        return columnNumber;
    }

    public final void setColumnNumber(final int columnNumber) {
        this.columnNumber = columnNumber <= COLUMN_MAX_CAP && columnNumber >= COLUMN_MIN_CAP ? columnNumber : COLUMN_MIN_CAP;
    }

    private List<AbstractNote> overlappingNotes(final List<AbstractNote> x2) {
        //lo stream fatto da gian cambiato per far si che non prenda una MyTrack ma una List<Note>
        return x2.stream().collect(Collectors.toMap(x -> x, x -> x2.stream()
                        .filter(y -> x != y && (x.getStartTime() == y.getStartTime()
                        || (x.getStartTime() < y.getStartTime() && (x.getStartTime() + x.getDuration().get()
                                        > y.getStartTime())))).collect(Collectors.toList()))).entrySet().stream()
                        .filter(x -> x.getValue().size() != 0)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
    }

    private List<Columns> getColumnList() {
        List<Columns> columnList = Arrays.stream(Columns.values()).collect(Collectors.toList());
        columnList.sort((e1, e2) -> e1.getNumericValue().compareTo(e2.getNumericValue()));
        return columnList;
    }

    private List<List<AbstractNote>> generateNoteRanges(final List<List<AbstractNote>> list) {
        List<Columns> columnList = getColumnList();
        noteRanges = list.stream().map(t -> {
                Columns column = columnList.remove(0);
                System.out.println(column);
                return t.stream().map(r -> {
                        return new NoteRange(column, r.getStartTime(), r.getStartTime()
                        + r.getDuration().get().intValue());
                }).collect(Collectors.toList());
        }).collect(Collectors.toList()).stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return list;
    }

    //return the max duration of a note in a track
    private Optional<Long> getMaxDuration(final MidiTrack track) {
        return track.getNotes().stream()
                .max((e1, e2) -> e1.getDuration().get().compareTo(e2.getDuration().get()))
                .get()
                .getDuration();
    }
    
    //return an int between 1 and 4 based on the duration of the note and the max duration of a note in the current track
    public static final int generateNoteHeight(final Optional<Long> noteDuration, final Optional<Long> maxDuration) {
        return IntStream.iterate(1, i -> i + 1)
        .limit(MAX_HEIGHT)
        .filter(x -> noteDuration.orElse(0L) <= maxDuration.orElse(0L) / MAX_HEIGHT * x)
        .findFirst()
        .orElse(1);
    }

    @Override
    public final List<List<LogicNoteImpl>> noteQueuing(final MidiTrack track) {
        //dice quante note ci sono per identifier
        List<Map.Entry<Integer, Long>> numberOfNotesForNoteType = new ArrayList<>(track.getNotes().stream()
                        .collect(Collectors.groupingBy(AbstractNote::getIdentifier, Collectors.counting())).entrySet());
        //raggruppa le note per identifier
        Map<Integer, List<AbstractNote>> notesForNoteType = track.getNotes().stream().collect(Collectors.groupingBy(
                        AbstractNote::getIdentifier, Collectors.toList()));
        //finch� non � minore del numero di colonne
        while (numberOfNotesForNoteType.size() > columnNumber) {
                //(dovrebbe) ordinare per numero di note dal minore al maggiore
            numberOfNotesForNoteType.sort((e1, e2) -> e1.getValue().compareTo(e2.getValue())); //questo funziona
                //System.out.println("current order");
                //numberOfNotesForNoteType.forEach(t -> System.out.println(t.getKey().toString() +" "+ t.getValue().toString()));
                //prendendo le prime 2 chiavi da sopra somma le due liste di note corrispondenti in una sola

                //non so se sia accettabile come codice, spero di si; forse � il caso di farci un metodo a se?
            notesForNoteType.put(numberOfNotesForNoteType.get(0).getKey(), 
                    Stream.of(notesForNoteType.get(numberOfNotesForNoteType.get(0).getKey()),
                    notesForNoteType.get(numberOfNotesForNoteType.get(1).getKey()))
                    .flatMap(x -> x.stream())
                    .collect(Collectors.toList()));
                //rimuove l'altra chiave
            notesForNoteType.remove(numberOfNotesForNoteType.get(1).getKey());
                //somma i quantitativi di note delle 2 liste in una sola
            numberOfNotesForNoteType.get(0).setValue(numberOfNotesForNoteType.get(0).getValue() 
                    + numberOfNotesForNoteType.get(1).getValue());
                //rimuove il secondo quantitativo
            numberOfNotesForNoteType.remove(1);
        }

//teoricamente elimina le collisioni
        List<Columns> columnList = getColumnList();
        return (generateNoteRanges(notesForNoteType.values().stream()
                .peek(x -> x.removeAll(overlappingNotes(x)))
                .collect(Collectors.toList())))
                .stream()
                .map(x -> {
                    Columns currentColumn = columnList.remove(0);
                    return x.stream()
                            .map(y -> 
                            new LogicNoteImpl(y, currentColumn,
                                    generateNoteHeight(y.getDuration(), getMaxDuration(track))))
                            .collect(Collectors.toList());
                })
                .collect(Collectors.toList());
    }

    @Override
    public final int verifyNote(final Columns column, final long start, final long end) {
        NoteRange currentRange = noteRanges.stream()
                .filter(x -> x.getColumn().equals(column))
                .filter(x -> x.getStart() < end)
                .sorted(Comparator.comparingLong(NoteRange::getStart))
                .findFirst()
                .orElse(noteRanges.get(0));
        int normalPoint = (int) ((end - start - Math.abs(currentRange.getEnd() - end)
                - Math.abs(currentRange.getStart() - start)) / (end - start)  * NOTE_POINT);
        this.combo = normalPoint >= NOTE_POINT - NOTE_TOLERANCE ? (this.combo < MAX_COMBO ? this.combo + 1 : this.combo) : 0;
        return ((normalPoint >= NOTE_POINT - NOTE_TOLERANCE 
                ? NOTE_POINT : (normalPoint + NOTE_TOLERANCE < 0 ? 0 
                        : normalPoint + NOTE_TOLERANCE)) + COMBO_POINT * combo) * columnNumber;

    }

}
