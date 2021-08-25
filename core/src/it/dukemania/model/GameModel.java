package it.dukemania.model;

import it.dukemania.midi.ParsedTrack;
import it.dukemania.midi.Song;

public class GameModel {
    private String songHash;
    private String playerName = "P1";
    private Song selectedSong;
    private ParsedTrack selectedTrack;
    private int numColumns;
    private int score;


    /**
     * @return The selected track number
     */
    public ParsedTrack getSelectedTrack() {
        return selectedTrack;
    }

    /**
     * Sets the selected track number.
     * @param selectedTrack The track number
     */
    public void setSelectedTrack(final ParsedTrack selectedTrack) {
        this.selectedTrack = selectedTrack;
    }

    /**
     * @return The selected columns on which to play the track
     */
    public int getNumColumns() {
        return numColumns;
    }

    /**
     * Sets the number of column on which to play the track.
     * @param numColumns The number of columns
     */
    public void setNumColumns(final int numColumns) {
        this.numColumns = numColumns;
    }

    /** Sets the selected song.
     * @param selectedSong The selected song
     */
    public void setSelectedSong(final Song selectedSong) {
        this.selectedSong = selectedSong;
    }

    /**
     * @return The selected song.
     */
    public Song getSelectedSong() {
        return selectedSong;
    }

    /**
     * Sets the hash of the song.
     * @param songHash The song hash
     */
    public void setSongHash(final String songHash) {
        this.songHash = songHash;
    }

    /**
     * Sets the player name.
     * @param playerName The player name
     */
    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
    }

    /**
     * @param score The score made by the player
     */
    public void setScore(final int score) {
        this.score = score;
    }

    /**
     * @return The selected song hash
     */
    public String getSongHash() {
        return songHash;
    }

    /**
     * @return The player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @return The score made by the player
     */
    public int getScore() {
        return score;
    }

}
