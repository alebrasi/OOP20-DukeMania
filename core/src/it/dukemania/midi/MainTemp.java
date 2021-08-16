package it.dukemania.midi;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

public class MainTemp {
    public static void main(final String[] args) throws InvalidMidiDataException, IOException {
        final MidiParser parse = new MidiParserImpl();
        final Song canzone = parse.parseMidi(/*"\\Users\\Laura\\Desktop\\midi_padre\\bellerep.mid");*/
                
                "\\Users\\Laura\\Desktop\\OOP"
                + "\\DukeMania-Testing\\res\\This-Game_-_Konomi-Suzuki.mid");
        System.out.println("\ntitlo:\t " + canzone.getTitle() + "\nBPM:\t\t " + canzone.getBPM() 
        + "\ndurata in microsec:\t " + canzone.getDuration() + "\nnumero tracce:\t " + canzone.getTracks().size());
        canzone.getTracks().forEach(t -> System.out.println("channel: " + t.getChannel() + "\t num note: " + t.getNotes().size() 
                /*+ "\t strumento: " + t.getInstrument() + "\t\t mappa (nota->Durata max): " + t.getNotesMaxDuration())*/));

    }

}
