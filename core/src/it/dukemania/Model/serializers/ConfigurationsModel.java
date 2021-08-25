package it.dukemania.Model.serializers;

import it.dukemania.Model.serializers.leaderboard.SongLeaderBoard;
import it.dukemania.Model.serializers.song.SongInfo;
import it.dukemania.Model.serializers.synthesizer.SynthInfo;

import java.io.IOException;
import java.util.List;

public interface ConfigurationsModel {
    /**
     * Read all the songs configuration.
     * @return A list containing the Songs' configuration
     * @throws IOException If it cannot access the configuration file
     */
    List<SongInfo> readSongsConfiguration() throws IOException;

    /**
     * Write the configurations of the songs.
     * @param songs A list of the songs to write
     * @throws IOException If it cannot write on the configuration file.
     */
    void writeSongsConfiguration(List<SongInfo> songs) throws IOException;

    /**
     * Read all the synthesizers configuration.
     * @return A list containing all the synthesizers' configuration
     * @throws IOException If it cannot access the configuration file
     */
    List<SynthInfo> readSynthesizersConfiguration() throws IOException;

    /**
     * @return Read all the leaderboards
     * @throws IOException If it cannot access the configuration file
     */
    List<SongLeaderBoard> readLeaderBoards() throws IOException;

    /**
     * Writes all the leaderboards.
     * @param leaderBoards A list containing all the leaderboards
     * @throws IOException If it cannot access the configuration file
     */
    void writeLeaderBoards(List<SongLeaderBoard> leaderBoards) throws IOException;
}
