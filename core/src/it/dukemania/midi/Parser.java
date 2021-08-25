package it.dukemania.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

public interface Parser {

    /**
     * this method parse the song file given.
     * @param myFile the file to be parsed
     * @return a song parsed
     * @throws InvalidMidiDataException
     * @throws IOException
     */
    Song parse(File myFile) throws InvalidMidiDataException, IOException;

}
