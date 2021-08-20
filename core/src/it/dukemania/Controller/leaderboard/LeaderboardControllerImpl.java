package it.dukemania.Controller.leaderboard;

import it.dukemania.Model.serializers.ConfigurationsModel;
import it.dukemania.Model.serializers.ConfigurationsModelImpl;
import it.dukemania.Model.serializers.leaderboard.SongLeaderBoard;
import it.dukemania.audioengine.Pair;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactoryImpl;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardControllerImpl implements LeaderboardController {

    private final Storage storage = new StorageFactoryImpl().getConfigurationStorage();
    private final ConfigurationsModel model = new ConfigurationsModelImpl(storage);

    @Override
    public List<Pair<String, String>> getLeaderboard(final String playerName, final Integer score, final String songHash) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        scores.setUserScore(playerName, score);
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
