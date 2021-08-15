package it.dukemania.util.storage;

import java.io.File;
import java.io.IOException;

public interface Storage {
    void writeStringOnFile(String filePath, String content) throws IOException;
    String readFileAsString(String filePath) throws IOException;
    byte[] readFileAsByte(String filePath) throws IOException;
    boolean createDirectory(String dirName);
    boolean createDirectoryRecursively(String path);
    boolean createFileIfNotExists(String path);
    String getDirectorySeparator();
    File getAsFile(String path);
    String getBaseDirectoryName();
}
