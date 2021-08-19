package it.dukemania.Model.serializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.dukemania.Model.serializers.song.SongInfo;
import it.dukemania.Model.serializers.synthesizer.SynthInfo;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactoryImpl;

import java.io.IOException;
import java.util.List;

interface ConfigurationsModel {
    List<SongInfo> readSongsConfiguration() throws IOException;
    void writeSongsConfiguration(List<SongInfo> songs) throws IOException;
    List<SynthInfo> readSynthesizersConfiguration() throws IOException;
}

public class Configuration implements ConfigurationsModel {
    private final Storage storage;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String SONGS_CONFIGURATION_PATH = "configs/song_config.json";
    private static final String SYNTHESIZERS_CONFIGURATION_PATH = "configs/synthesizers_config.json";

    public Configuration(final Storage storage) {
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
        String json = storage.readFileAsString(SYNTHESIZERS_CONFIGURATION_PATH);

        JavaType listSynthInfoType = mapper.constructType(new TypeReference<List<SynthInfo>>() {
        });

        return mapper.readValue(json, listSynthInfoType);
    }
}

/*
public class Configuration {

    public static class Song {

        private final ObjectMapper mapper = new ObjectMapper();
        private final Storage configurationStorage = new StorageFactoryImpl().getConfigurationStorage();

        private static final String SONGS_CONFIGURATION_PATH = "configs/song_config.json";

        public List<SongInfo> readAll() throws IOException {
            String json = configurationStorage.readFileAsString(SONGS_CONFIGURATION_PATH);

            JavaType listSongType = mapper.constructType(new TypeReference<List<SongInfo>>() {
            });

            return mapper.readValue(json, listSongType);
        }

        public void writeAll(final List<SongInfo> songs) throws IOException {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(songs);
            configurationStorage.createFileIfNotExists(SONGS_CONFIGURATION_PATH);
            configurationStorage.writeStringOnFile(SONGS_CONFIGURATION_PATH, json);
        }
    }

    public static class Synthesizer {

        private final ObjectMapper mapper = new ObjectMapper();
        private final Storage configurationStorage = new StorageFactoryImpl().getConfigurationStorage();
        private static final String SYNTHESIZERS_CONFIGURATION_PATH = "configs/synthesizers_config.json";

        public List<SynthInfo> readAll() throws IOException {
            String json = configurationStorage.readFileAsString(SYNTHESIZERS_CONFIGURATION_PATH);

            JavaType listSynthInfoType = mapper.constructType(new TypeReference<List<SynthInfo>>() {
            });

            return mapper.readValue(json, listSynthInfoType);
        }

        public void writeAll(final List<SynthInfo> synths) throws IOException {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(synths);
            configurationStorage.createFileIfNotExists(SYNTHESIZERS_CONFIGURATION_PATH);
            configurationStorage.writeStringOnFile(SYNTHESIZERS_CONFIGURATION_PATH, json);
        }
    }
}

 */
