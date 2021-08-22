package it.dukemania.Model;

import it.dukemania.midi.MidiTrack;
import it.dukemania.midi.Song;

public class GameModel {
    private String songHash;
    private String playerName = "P1";
    private Song selectedSong;
    private MidiTrack selectedTrack;
    private int numColumn;

    public int getNumColumn() {
        return numColumn;
    }

    public void setNumColumn(int numColumn) {
        this.numColumn = numColumn;
    }

    public MidiTrack getSelectedTrack() {
        return selectedTrack;
    }

    public void setSelectedTrack(final MidiTrack selectedTrack) {
        this.selectedTrack = selectedTrack;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(final int numColumns) {
        this.numColumns = numColumns;
    }

    private int numColumns;
    private int score;

    public void setSelectedSong(final Song selectedSong) {
        this.selectedSong = selectedSong;
    }

    public Song getSelectedSong() {
        return selectedSong;
    }

    public void setSongHash(final String songHash) {
        this.songHash = songHash;
    }

    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public String getSongHash() {
        return songHash;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

}
