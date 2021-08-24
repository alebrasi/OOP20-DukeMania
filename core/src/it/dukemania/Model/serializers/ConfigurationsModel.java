package it.dukemania.Model.serializers;

import it.dukemania.Model.serializers.leaderboard.SongLeaderBoard;
import it.dukemania.Model.serializers.song.SongInfo;
import it.dukemania.Model.serializers.synthesizer.SynthInfo;

import java.io.IOException;
import java.util.List;

public interface ConfigurationsModel {
    List<SongInfo> readSongsConfiguration() throws IOException;

    void writeSongsConfiguration(List<SongInfo> songs) throws IOException;

    List<SynthInfo> readSynthesizersConfiguration() throws IOException;

    List<SongLeaderBoard> readLeaderBoards() throws IOException;

    void writeLeaderBoards(List<SongLeaderBoard> leaderBoards) throws IOException;
}
