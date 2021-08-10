package it.dukemania.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


import it.dukemania.midi.Note;
import it.dukemania.midi.MyTrack;
import it.dukemania.midi.Song;

public class TrackFilterImpl implements TrackFilter {

    static final int MAX_NOTE = 600;

    private Collection<MyTrack> selectTrack(final Song song) {
        return song.getTracks().stream()
                .filter(x -> x.getNotes().size() > MAX_NOTE)
                .collect(Collectors.toList());
    }

    @Override
    public final Collection<MyTrack> reduceTrack(final Song song) {
        int counter;
        List<Note> notes;
        for (MyTrack track:song.getTracks()) { 
                        if (selectTrack(song).contains(track)) {
                                counter = 0;
                                notes = new ArrayList<>(track.getNotes());
                                //grossomodo determina ogni quante note se ne salva una dal filtro per ridurre il numero
                                //sotto a MAX_NOTE ed elimina tutte le altre
                                for (Note note : notes) {
                                        //non sono sicuro mantenga l'ordine delle note ogni volta ma credo di si
                                        if (counter != (notes.size() / MAX_NOTE)) {
                                                track.deleteNote(note); //metodo aggiunnto da me, elimina una nota dalla traccia
                                                counter += 1;
                                        }
                                        else {
                                                counter = 0;
                                        }
                                }
                        }
                }
        return song.getTracks();
    }

}
