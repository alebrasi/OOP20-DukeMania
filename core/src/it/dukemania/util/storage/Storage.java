package it.dukemania.util.storage;

import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.IOException;

public interface Storage {
    /**
     * Write a string on a file.
     * @param filePath The path of the file
     * @param content The content to write
     * @throws IOException If it cannot access the file
     */
    void writeStringOnFile(String filePath, String content) throws IOException;

    /**
     * Read a file as a string.
     * @param filePath The path of the file
     * @return The file content
     * @throws IOException If it cannot access the file
     */
    String readFileAsString(String filePath) throws IOException;

    /**
     * Read the file as a byte array.
     * @param filePath The path of the file
     * @return The file content
     * @throws IOException If it cannot access the file
     */
    byte[] readFileAsByte(String filePath) throws IOException;

    /**
     * Creates a directory.
     * @param dirName The directory name
     * @return True if the directory has been created, false otherwise
     */
    boolean createDirectory(String dirName);

    /**
     * Creates a directory at the specified path. If the directories contained in the path don't exists, provides to create them.
     * @param path The directory path
     * @return True if the directory has been created, false otherwise
     */
    boolean createDirectoryRecursively(String path);

    /**
     * Creates a file if it not exists. If the directories contained in the path don't exists, provides to create them.
     * @param path The file path
     * @return True of the file has benn created, false otherwise
     */
    boolean createFileIfNotExists(String path);

    /**
     * Returns the specified file as a java.io.File.
     * @param path The file Path
     * @return The File
     */
    File getAsFile(String path);

    /**
     * Returns the base storage directory name.
     * @return The base directory name
     */
    String getBaseDirectoryName();

    /**
     * Returns the specified file as a com.badlogic.gdx.files.FileHandle.
     * @param path The file path
     * @return The FileHandle
     */
    FileHandle getAsFileHandle(String path);

    /**
     * Copies the file to another path.
     * @param source The file path source
     * @param destination The folder destination path
     * @throws IOException If it cannot copy the file
     */
    void copyTo(String source, String destination) throws IOException;
}
