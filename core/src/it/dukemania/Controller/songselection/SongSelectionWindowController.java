package it.dukemania.Controller.songselection;

import it.dukemania.Model.serializers.song.SongInfo;
import it.dukemania.midi.InstrumentType;
import it.dukemania.windowmanager.SwitchWindowNotifier;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.util.List;

public interface SongSelectionWindowController {
    void openSong(String path) throws InvalidMidiDataException, IOException;
    void setPlayTrack(int trackNumber);
    void updateTracks(List<String> names, List<InstrumentType> instruments);
    void playSong(SwitchWindowNotifier notifier) throws InvalidMidiDataException, IOException;
    void setColumnsNumber(int columns);
    SongInfo getSongInfo();
    String[] getAllInstruments();
    Integer[] getNumOfCols();
}
