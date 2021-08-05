package it.dukemania.View.menu;

public class Track {

    final private String trackName;
    final private String instrumentName;
    final private int trackID;

    public Track(final int trackID, final String trackName, final String instrumentName) {
        this.trackName = trackName;
        this.instrumentName = instrumentName;
        this.trackID = trackID;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getInstrumentName() {
        return instrumentName;
    }


    public int getTrackID() {
        return trackID;
    }
}
