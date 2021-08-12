package it.dukemania.util.storage;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;

public class StorageFactoryImpl implements StorageFactory {

    private static String configFolderName = ".dukemania";
    private static String homeFolderPath = System.getProperty("user.home");
    private static String separator = File.separator;
    private static String configPath = homeFolderPath + separator + configFolderName;
    private final Function<String, File> assetMappingFunction = (path) -> Gdx.files.internal(path).file();
    private final Function<String, File> configurationMappingFunction = (path) -> new File(configPath + separator + path);
    private final Function<String, File> externalMappingFunction = File::new;

    @Override
    public Storage getAssetStorage() {
        return new StorageImpl(assetMappingFunction);
    }

    @Override
    public Storage getConfigurationStorage() {
        return new StorageImpl(configurationMappingFunction);
    }

    @Override
    public Storage getExternalStorage(final String path) {
        return new StorageImpl(externalMappingFunction);
    }

    private final class StorageImpl implements Storage {

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
            System.out.println(fileMapping.apply(filePath).toPath());
            return Files.readString(fileMapping.apply(filePath).toPath());
        }

        @Override
        public boolean createDirectory(final String dirName) {
            return fileMapping.apply(dirName).mkdir();
        }

        @Override
        public String getDirectorySeparator() {
            return separator;
        }

        @Override
        public File getAsFile(final String path) {
            File a = new File("asd");
            return fileMapping.apply(path);
        }

        @Override
        public String getBaseDirectoryName() {
            return null;
        }
    }
}
