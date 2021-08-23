package it.dukemania.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

public interface MidiParser {

    /**
     * this method parse the midi song file given.
     * @param myMidi
     * @return a song parsed
     * @throws InvalidMidiDataException
     * @throws IOException
     */
    Song parseMidi(File myMidi) throws InvalidMidiDataException, IOException;

}
