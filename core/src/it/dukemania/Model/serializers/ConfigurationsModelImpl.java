package it.dukemania.Model.serializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.dukemania.Model.serializers.leaderboard.SongLeaderBoard;
import it.dukemania.Model.serializers.song.SongInfo;
import it.dukemania.Model.serializers.synthesizer.SynthInfo;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactoryImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

//TODO add generic method for reading and writing
public class ConfigurationsModelImpl implements ConfigurationsModel {
    private final Storage storage;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String SONGS_CONFIGURATION_PATH = "configs/song_config.json";
    private static final String SYNTHESIZERS_CONFIGURATION_NAME = "synthesizers_config.json";
    private static final String SYNTHESIZERS_CONFIGURATION_PATH = "configs/" + SYNTHESIZERS_CONFIGURATION_NAME;
    private static final String SONGS_LEADERBOARDS_PATH = "configs/users_score.json";

    public ConfigurationsModelImpl(final Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<SongInfo> readSongsConfiguration() throws IOException {
        String json = storage.readFileAsString(SONGS_CONFIGURATION_PATH);

        JavaType listSongType = mapper.constructType(new TypeReference<List<SongInfo>>() {
        });

        return mapper.readValue(json, listSongType);
    }

    @Override
    public void writeSongsConfiguration(final List<SongInfo> songs) throws IOException {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(songs);
        storage.createFileIfNotExists(SONGS_CONFIGURATION_PATH);
        storage.writeStringOnFile(SONGS_CONFIGURATION_PATH, json);
    }

    @Override
    public List<SynthInfo> readSynthesizersConfiguration() throws IOException {
        //Copies the synthesizers configurations to the configuration folder if it not exists
        if (!storage.getAsFile(SYNTHESIZERS_CONFIGURATION_PATH).exists()) {
            try {
                Storage assetStorage = new StorageFactoryImpl().getAssetStorage();
                assetStorage.copyTo("json" + File.separator + SYNTHESIZERS_CONFIGURATION_NAME,
                        storage.getBaseDirectoryName() + File.separator + SYNTHESIZERS_CONFIGURATION_PATH);
            } catch (Exception ignored) {
            }
        }
        String json = storage.readFileAsString(SYNTHESIZERS_CONFIGURATION_PATH);

        JavaType listSynthInfoType = mapper.constructType(new TypeReference<List<SynthInfo>>() {
        });

        return mapper.readValue(json, listSynthInfoType);
    }

    @Override
    public List<SongLeaderBoard> readLeaderBoards() throws IOException {
        String json = storage.readFileAsString(SONGS_LEADERBOARDS_PATH);

        JavaType listSongLeaderBoardType = mapper.constructType(new TypeReference<List<SongLeaderBoard>>() {
        });

        return mapper.readValue(json, listSongLeaderBoardType);
    }

    @Override
    public void writeLeaderBoards(final List<SongLeaderBoard> leaderBoards) throws IOException {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(leaderBoards);
        storage.createFileIfNotExists(SONGS_LEADERBOARDS_PATH);
        storage.writeStringOnFile(SONGS_LEADERBOARDS_PATH, json);
    }
}
