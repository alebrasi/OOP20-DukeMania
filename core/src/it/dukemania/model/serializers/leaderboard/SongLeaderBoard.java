package it.dukemania.model.serializers.leaderboard;

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

    /**
     * @return The hash of the song
     */
    public String getSongHash() {
        return this.songHash;
    }

    /**
     * Sets the score of a user.
     * @param playerName The player name
     * @param score The player's score
     */
    public void setUserScore(final String playerName, final Integer score) {
        usersScore.computeIfPresent(playerName, (p, s) -> s < score ? score : s);
        usersScore.computeIfAbsent(playerName, s -> score);
    }

    /**
     * @return A map of the users' score
     */
    public Map<String, Integer> getUsersScore() {
        return Map.copyOf(usersScore);
    }
}
