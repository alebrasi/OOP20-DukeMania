package it.dukemania.midi;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

public interface MidiParser {

    Song parseMidi(String file) throws InvalidMidiDataException, IOException;

}
