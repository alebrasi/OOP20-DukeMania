package it.dukemania.controller.leaderboard;

import it.dukemania.util.Pair;

import java.util.List;

public interface LeaderboardController {
     /**
      * @return A list containing all the leaderboards
      */
     List<Pair<String, String>> getLeaderboard();
}
