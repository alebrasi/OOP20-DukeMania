package it.dukemania.util.storage;

import com.badlogic.gdx.Gdx;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StorageFactoryImpl implements StorageFactory {

    private static final String CONFIG_FOLDER_NAME = ".dukemania";
    private static final String USER_HOME_PATH = System.getProperty("user.home");
    private static final String FILE_SEPARATOR = "/";
    private static final String CONFIGS_PATH = USER_HOME_PATH + FILE_SEPARATOR + CONFIG_FOLDER_NAME;

    private final Function<String, File> configurationMappingFunction = (path) -> new File(CONFIGS_PATH + FILE_SEPARATOR + path);
    private final Function<String, File> externalMappingFunction = File::new;
    private final Function<String, File> assetMappingFunction = (path) -> {
        if (Gdx.files.internal(path).file().exists()) {
            return Gdx.files.internal((path)).file().getAbsoluteFile();
        }
        return Gdx.files.internal("core" + FILE_SEPARATOR + "assets" + FILE_SEPARATOR + path).file().getAbsoluteFile();
    };

    @Override
    public Storage getAssetStorage() {
        return new StorageImpl(assetMappingFunction);
    }

    @Override
    public Storage getConfigurationStorage() {
        return new StorageImpl(configurationMappingFunction);
    }

    @Override
    public Storage getExternalStorage() {
        return new StorageImpl(externalMappingFunction);
    }

    private static final class StorageImpl implements Storage {

        private final Function<String, File> fileMapping;

        private StorageImpl(final Function<String, File> fileMappingFunction) {
            this.fileMapping = fileMappingFunction;
        }

        @Override
        public void writeStringOnFile(final String filePath, final String content) throws IOException {
            Files.writeString(fileMapping.apply(filePath).toPath(), content);
        }

        @Override
        public String readFileAsString(final String filePath) throws IOException {
            return Files.readString(fileMapping.apply(filePath).toPath());
        }

        @Override
        public byte[] readFileAsByte(final String filePath) throws IOException {
            return Files.readAllBytes(fileMapping.apply(filePath).toPath());
        }

        @Override
        public boolean createDirectory(final String dirName) {
            return fileMapping.apply(dirName).mkdir();
        }

        @Override
        public boolean createDirectoryRecursively(final String path) {
            return fileMapping.apply(path).mkdirs();
        }

        @Override
        public boolean createFileIfNotExists(final String path) {
            String[] dirs = path.split(FILE_SEPARATOR);
            String tmpPath = Arrays.stream(dirs).limit(dirs.length - 1).collect(Collectors.joining(FILE_SEPARATOR));
            createDirectoryRecursively(tmpPath);
            try {
                return fileMapping.apply(path).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public String getDirectorySeparator() {
            return FILE_SEPARATOR;
        }

        @Override
        public File getAsFile(final String path) {
            return fileMapping.apply(path);
        }

        @Override
        public String getBaseDirectoryName() {
            return null;
        }
    }
}
