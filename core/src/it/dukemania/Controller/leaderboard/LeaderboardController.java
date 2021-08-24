package it.dukemania.Controller.leaderboard;

import it.dukemania.util.Pair;

import java.util.List;

public interface LeaderboardController {
     List<Pair<String, String>> getLeaderboard();
}
