package it.dukemania.util.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StorageFactoryImpl implements StorageFactory {

    private static final String CONFIG_FOLDER_NAME = ".dukemania";
    private static final String USER_HOME_PATH = System.getProperty("user.home");
    private static final String FILE_SEPARATOR = File.separator;
    private static final String CONFIGS_PATH = USER_HOME_PATH + FILE_SEPARATOR + CONFIG_FOLDER_NAME;

    private final Function<String, FileHandle> configurationMappingFunction = path -> {
        return new FileHandle(new File(CONFIGS_PATH + FILE_SEPARATOR + path));
    };
    private final Function<String, FileHandle> externalMappingFunction = path -> new FileHandle(new File(path));
    private final Function<String, FileHandle> assetMappingFunction = path -> Gdx.files.internal(path);

    @Override
    public Storage getAssetStorage() {
        return new LocalStorageImpl(assetMappingFunction);
    }

    @Override
    public Storage getConfigurationStorage() {
        return new LocalStorageImpl(configurationMappingFunction);
    }

    @Override
    public Storage getExternalStorage() {
        return new LocalStorageImpl(externalMappingFunction);
    }

    private static final class LocalStorageImpl implements Storage {

        private final Function<String, FileHandle> fileMapping;

        private LocalStorageImpl(final Function<String, FileHandle> fileMappingFunction) {
            this.fileMapping = fileMappingFunction;
        }

        @Override
        public void writeStringOnFile(final String filePath, final String content) throws IOException {
            Files.write(fileMapping.apply(filePath).file().getAbsoluteFile().toPath(), content.getBytes());
        }

        @Override
        public String readFileAsString(final String filePath) throws IOException {
            return Files.readString(fileMapping.apply(filePath).file().getAbsoluteFile().toPath());
        }

        @Override
        public byte[] readFileAsByte(final String filePath) throws IOException {
            return Files.readAllBytes(fileMapping.apply(filePath).file().getAbsoluteFile().toPath());
        }

        @Override
        public boolean createDirectory(final String dirName) {
            return fileMapping.apply(dirName).file().getAbsoluteFile().mkdir();
        }

        @Override
        public boolean createDirectoryRecursively(final String path) {
            return fileMapping.apply(path).file().getAbsoluteFile().mkdirs();
        }

        @Override
        public boolean createFileIfNotExists(final String path) {
            String tmpSeparator = FILE_SEPARATOR.equals("/") ? FILE_SEPARATOR : FILE_SEPARATOR + FILE_SEPARATOR;
            String[] dirs = path.split(tmpSeparator);
            String tmpPath = Arrays.stream(dirs).limit(dirs.length - 1).collect(Collectors.joining(tmpSeparator));
            createDirectoryRecursively(tmpPath);
            try {
                return fileMapping.apply(path).file().getAbsoluteFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public File getAsFile(final String path) {
            return fileMapping.apply(path).file();
        }

        @Override
        public String getBaseDirectoryName() {
            return fileMapping.apply("").file().getAbsoluteFile().getAbsolutePath();
        }

        @Override
        public FileHandle getAsFileHandle(final String path) {
            return fileMapping.apply(path);
        }

        @Override
        public void copyTo(final String source, final String destination) throws IOException {
            String tmpDestination = (destination.contains("/") && !File.separator.equals("/")) ? destination.replace("/", "\\")
                                                                                                : destination;
            createFileIfNotExists(tmpDestination);
            File file = new File(tmpDestination);
            InputStream in = fileMapping.apply(source).read();
            FileOutputStream outStream = new FileOutputStream(file, false);
            outStream.write(in.readAllBytes());
        }
    }
}
