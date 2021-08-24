package it.dukemania.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

public class MainTemp {
    public static void main(final String[] args) throws InvalidMidiDataException, IOException {
        final Parser parse = MidiParser.getInstance();
        final Song canzone = parse.parse(/*"\\Users\\Laura\\Desktop\\midi_padre\\bellerep.mid");*/
                new File(
                "\\Users\\Laura\\Desktop\\OOP"
                + "\\DukeMania-Testing\\res\\This-Game_-_Konomi-Suzuki.mid"));
        System.out.println("\ntitlo:\t " + canzone.getTitle() + "\nBPM:\t\t " + canzone.getBPM() 
        + "\ndurata in microsec:\t " + canzone.getDuration() + "\nnumero tracce:\t " + canzone.getTracks().size());
        canzone.getTracks()
                .stream()
                .forEach(t -> System.out.println("channel: " + t.getChannel() + "\t num note: " + t.getNotes().size()));
        canzone.getTracks()
                .stream()
                .filter(t -> t.getChannel() != 10)
                .map(t -> (TrackImpl) t)
                .forEach(t -> System.out.println("\t strumento: " + t.getInstrument() + "\t\t mappa (nota->Durata max): " 
                + t.getNotesMaxDuration()));

    }

}
