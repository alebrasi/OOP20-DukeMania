package it.dukemania.Model.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.dukemania.Model.Song;
import it.dukemania.Model.serializers.synthesizer.SynthInfo;
import it.dukemania.audioengine.SynthBuilder;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactoryImpl;

import java.io.IOException;
import java.util.List;

public class Configuration {

    public static class song {

        private final ObjectMapper mapper = new ObjectMapper();
        private final Storage configurationStorage = new StorageFactoryImpl().getConfigurationStorage();

        private final String SONGS_CONFIGURATION_PATH = "configs/song_config.json";

        public List<Song> readAll() throws IOException {
            String json = configurationStorage.readFileAsString(SONGS_CONFIGURATION_PATH);

            JavaType listSongType = mapper.constructType(new TypeReference<List<Song>>() {
            });

            return mapper.readValue(json, listSongType);
        }

        public void writeAll(final List<Song> songs) throws IOException {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(songs);
            configurationStorage.createFileIfNotExists(SONGS_CONFIGURATION_PATH);
            configurationStorage.writeStringOnFile(SONGS_CONFIGURATION_PATH, json);
        }
    }

    public static class synthesizer {

        private final ObjectMapper mapper = new ObjectMapper();
        private final Storage configurationStorage = new StorageFactoryImpl().getConfigurationStorage();
        private final String SYNTHESIZERS_CONFIGURATION_PATH = "configs/synthesizers_config.json";

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
