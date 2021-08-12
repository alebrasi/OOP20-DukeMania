package it.dukemania.Model.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.dukemania.Model.Song;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactoryImpl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Configuration {

    public static class song {

        private final ObjectMapper mapper = new ObjectMapper();
        private final Storage configurationStorage = new StorageFactoryImpl().getConfigurationStorage();
        private final String songConfigurationPath = "song_config.json";

        public List<Song> readAll() throws IOException {

            String json = configurationStorage.readFileAsString(songConfigurationPath);

            List<Song> songs = Collections.emptyList();
            try {

                JavaType listSongType = mapper.constructType(new TypeReference<List<Song>>() {
                });

                songs = mapper.readValue(json, listSongType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return songs;
        }

        public void writeAll(final List<Song> songs) {
            String json = "";

            try {
                json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(songs);
                configurationStorage.writeStringOnFile(songConfigurationPath, json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
