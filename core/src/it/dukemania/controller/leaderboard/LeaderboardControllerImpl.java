package it.dukemania.controller.leaderboard;

import it.dukemania.model.GameModel;
import it.dukemania.model.serializers.ConfigurationsModel;
import it.dukemania.model.serializers.ConfigurationsModelImpl;
import it.dukemania.model.serializers.leaderboard.SongLeaderBoard;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactoryImpl;
import it.dukemania.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderboardControllerImpl implements LeaderboardController {

    private final Storage storage = new StorageFactoryImpl().getConfigurationStorage();
    private final ConfigurationsModel model = new ConfigurationsModelImpl(storage);
    private final GameModel data;

    public LeaderboardControllerImpl(final GameModel data) {
        this.data = data;
    }

    @Override
    public final List<Pair<String, String>> getLeaderboard() {
        String songHash = data.getSongHash();
        SongLeaderBoard scores = new SongLeaderBoard(songHash, new HashMap<>());
        List<SongLeaderBoard> leaderBoards = new ArrayList<>();
        try {
            leaderBoards = model.readLeaderBoards();
            scores = leaderBoards
                            .stream()
                            .filter(l -> l.getSongHash().equals(songHash))
                            .findFirst()
                            .orElse(new SongLeaderBoard(songHash, new HashMap<>()));
            leaderBoards.remove(scores);
        } catch (Exception ignored) {
        }
        scores.setUserScore(data.getPlayerName(), data.getScore());
        leaderBoards.add(scores);
        try {
            model.writeLeaderBoards(leaderBoards);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scores.getUsersScore().entrySet().stream().map(l -> new Pair<>(l.getKey(), l.getValue().toString()))
                                                        .sorted(Comparator.comparing(e -> -Integer.parseInt(e.getY())))
                                                        .collect(Collectors.toList());
    }
}
