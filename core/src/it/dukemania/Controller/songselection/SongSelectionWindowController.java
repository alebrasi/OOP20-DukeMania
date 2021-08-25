package it.dukemania.Controller.songselection;

import it.dukemania.Model.serializers.song.SongInfo;
import it.dukemania.midi.InstrumentType;
import it.dukemania.windowmanager.SwitchWindowNotifier;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.util.List;

public interface SongSelectionWindowController {
    /**
     * Sets the song path.
     * @param path The song path.
     * @throws InvalidMidiDataException The given path doesn't represent a valid MIDI file
     * @throws IOException The given path is not a valid path or file
     */
    void setSongPath(String path) throws InvalidMidiDataException, IOException;

    /**
     * Sets the track to play.
     * @param trackNumber The track number
     */
    void setPlayTrack(int trackNumber);

    /**
     * Updates the track configurations.
     * @param names The list of the track names
     * @param instruments The list of the track instruments
     */
    void updateTracks(List<String> names, List<InstrumentType> instruments);

    /**
     * Instruct the controller to switch window to the PlayScreen.
     * @throws InvalidMidiDataException
     * @throws IOException
     */
    void playSong() throws InvalidMidiDataException, IOException;

    /** Sets the number of columns.
     * @param columns The number of columns
     */
    void setColumnsNumber(int columns);

    /**
     * @return Return the information of a song.
     */
    SongInfo getSongInfo();

    /**
     * Returns all the MIDI instruments.
     * @return An array containing all the instruments
     */
    String[] getAllInstruments();

    /**
     * Returns the number of the possible playing columns.
     * @return An array containing the columns.
     */
    Integer[] getNumOfCols();
}
