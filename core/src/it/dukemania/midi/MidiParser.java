package it.dukemania.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

public interface MidiParser {

    Song parseMidi(File myMidi) throws InvalidMidiDataException, IOException;

}
