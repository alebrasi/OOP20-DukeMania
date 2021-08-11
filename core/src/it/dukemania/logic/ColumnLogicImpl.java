package it.dukemania.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.dukemania.midi.MyTrack;
import it.dukemania.midi.Note;

public class ColumnLogicImpl implements ColumnLogic {

    private static final int COLUMN_MAX_CAP = 8;
    private static final int COLUMN_MIN_CAP = 4;
    private static final int NOTE_POINT = 100;
    private static final int NOTE_TOLERANCE = 10;
    private static final int MAX_COMBO = 20;
    private static final int COMBO_POINT = 5;
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

    @Override
    public final void increaseColumnNumber() {
        this.columnNumber = (columnNumber + 1 <= COLUMN_MAX_CAP) ? this.columnNumber + 1 : this.columnNumber;
    }

    @Override
    public final void decreaseColumnNumber() {
        this.columnNumber = (columnNumber - 1 >= COLUMN_MIN_CAP) ? this.columnNumber - 1 : this.columnNumber;
    }

    private List<Note> overlappingNotes(final List<Note> notes) {
        //print di verifica funzionamento
        //Map<Note, List<Note>> collisioni = notes.stream().collect(Collectors.toMap(x->x, x->notes.stream().filter(y -> x!=y && (x.getStartTime() == y.getStartTime() || (x.getStartTime() < y.getStartTime() && (x.getStartTime() + x.getDuration().get() > y.getStartTime())))).collect(Collectors.toList()))).entrySet().stream().filter(x->x.getValue().size()!=0).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        //System.out.println("col"+collisioni.size());
        //collisioni.forEach((k,v) -> System.out.println(k.getStartTime() +" "+ k.getDuration() +" "+ v.size()));
        //lo stream fatto da gian cambiato per far si che non prenda una MyTrack ma una List<Note>
        return notes.stream().collect(Collectors.toMap(x -> x, x -> notes.stream()
                        .filter(y -> x != y && (x.getStartTime() == y.getStartTime()
                        || (x.getStartTime() < y.getStartTime() && (x.getStartTime() + x.getDuration().get()
                                        > y.getStartTime())))).collect(Collectors.toList()))).entrySet().stream()
                        .filter(x -> x.getValue().size() != 0)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
    }

    @Override
    public final List<List<Note>> noteQueuing(final MyTrack track) {
        //dice quante note ci sono per tipo
        List<Map.Entry<Integer, Long>> numberOfNotesForNoteType = new ArrayList<>(track.getNotes().stream()
                        .collect(Collectors.groupingBy(Note::getIdentifier, Collectors.counting())).entrySet());
        //raggruppa le note per tipo
        Map<Integer, List<Note>> notesForNoteType = track.getNotes().stream().collect(Collectors.groupingBy(
                        Note::getIdentifier, Collectors.toList()));
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
                        .collect(Collectors.toList()
                                                ));
                //rimuove l'altra chiave
                notesForNoteType.remove(numberOfNotesForNoteType.get(1).getKey());
                //somma i quantitativi di note delle 2 liste in una sola
                numberOfNotesForNoteType.get(0).setValue(numberOfNotesForNoteType.get(0).getValue() 
                                + numberOfNotesForNoteType.get(1).getValue());
                //rimuove il secondo quantitativo
                numberOfNotesForNoteType.remove(1);
        }
//teoricamente elimina le collisioni
        return generateNoteRanges(notesForNoteType.values().stream()
                        .peek(t -> t.removeAll(overlappingNotes(t)))
                        .collect(Collectors.toList()));

    }

    private List<List<Note>> generateNoteRanges(final List<List<Note>> collect) {
        // TODO Auto-generated method stub
        return collect;
    }

    @Override
    public final int verifyNote(final ColumnsEnum column, final int start, final int end) {
        // TODO Auto-generated method stub
        return 0;
    }

}
