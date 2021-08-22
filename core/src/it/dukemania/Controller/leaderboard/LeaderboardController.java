package it.dukemania.Controller.leaderboard;

import it.dukemania.Model.serializers.leaderboard.SongLeaderBoard;
import it.dukemania.audioengine.Pair;

import java.util.List;

public interface LeaderboardController {
     List<Pair<String, String>> getLeaderboard();
}
