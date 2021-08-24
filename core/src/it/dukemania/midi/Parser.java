package it.dukemania.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

public interface Parser {

    /**
     * this method parse the midi song file given.
     * @param myMidi
     * @return a song parsed
     * @throws InvalidMidiDataException
     * @throws IOException
     */
    Song parse(File myMidi) throws InvalidMidiDataException, IOException;

}
