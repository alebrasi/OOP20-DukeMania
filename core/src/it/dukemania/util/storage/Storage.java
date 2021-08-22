package it.dukemania.util.storage;

import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Storage {
    void writeStringOnFile(String filePath, String content) throws IOException;
    String readFileAsString(String filePath) throws IOException;
    byte[] readFileAsByte(String filePath) throws IOException;
    boolean createDirectory(String dirName);
    boolean createDirectoryRecursively(String path);
    boolean createFileIfNotExists(String path);
    File getAsFile(String path);
    String getBaseDirectoryName();
    FileHandle getAsFileHandle(String path);
    void copyTo(String source, String destination) throws IOException;
}
