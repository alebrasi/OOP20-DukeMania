package it.dukemania.Model.serializers.leaderboard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;

@JsonDeserialize(using = SongLeaderBoardDeserializer.class)
@JsonSerialize(using = SongLeaderBoardSerializer.class)
public class SongLeaderBoard {
    private final String songHash;
    private final Map<String, Integer> usersScore;

    public SongLeaderBoard(final String songHash, final Map<String, Integer> usersScore) {
        this.songHash = songHash;
        this.usersScore = usersScore;
    }

    public String getSongHash() {
        return this.songHash;
    }

    public void setUserScore(final String playerName, final Integer score) {
        usersScore.computeIfPresent(playerName, (p, s) -> s < score ? score : s);
        usersScore.computeIfAbsent(playerName, s -> score);
    }

    public Map<String, Integer> getUsersScore() {
        return Map.copyOf(usersScore);
    }
}
