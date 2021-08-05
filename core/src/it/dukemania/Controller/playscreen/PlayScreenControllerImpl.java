package it.dukemania.Controller.playscreen;

import it.dukemania.View.menu.Track;

import java.util.ArrayList;
import java.util.List;

public class PlayScreenControllerImpl implements PlayScreenController {

    @Override
    public void openSong(final String path) {

    }

    @Override
    public void setPlayTrack(final int trackNumber) {

    }

    @Override
    public List<Track> getTracks() {
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track(1, "asd", "Bass"));
        tracks.add(new Track(2, "sasas", "Guitar"));
        tracks.add(new Track(3, "sahghs", "Drums"));
        tracks.add(new Track(4, "sdfgdf", "Keyboard"));
        tracks.add(new Track(5, "sdfasfg", "Koto"));
        tracks.add(new Track(6, "sdfasfg", "Sax"));
        tracks.add(new Track(7, "sdffhsjdfhasdag", "KeyBoard"));
        tracks.add(new Track(8, "sdfasfg", "Guitar"));
        tracks.add(new Track(9, "sdfasfg", "Sax"));
        return tracks;
    }

    @Override
    public String[] getAllInstruments() {

        String[] instruments = { "Bass", "Guitar", "Drums", "Keyboard", "Koto", "Sax" };

        return instruments;
    }
}
