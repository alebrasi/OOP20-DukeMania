package it.dukemania.Controller.filedialog;

import java.nio.file.NotDirectoryException;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public interface FileDialogController {
    /**
     * Sets the regex filter.
     * @param filter The regex filter
     * @throws PatternSyntaxException The regex filter is not valid
     */
    void setFilter(String filter) throws PatternSyntaxException;

    /**
     * Goes back one folder from the current one.
     */
    void stepBackFromCurrentDirectory();

    /**
     * @return Returns a list of files that are present in the current directory
     */
    List<FileInfo> getFilesInCurrentDirectory();

    /**
     * @return Returns the directory separator
     */
    String getDirectorySeparator();

    /**
     * Sets the current directory to the root directory.
     */
    void returnToRootDirectory();

    /**
     * Advance in child directory from the current one.
     * @param directoryName The directory to advance.
     * @return True if it advances, false otherwise
     */
    boolean advanceInDirectory(String directoryName);

    /**
     * Sets the FileSelectedListener.
     * @param listener The listener
     */
    void setSelectedFileListener(FileSelectedListener listener);

    /**
     * Sets the root directory of the FileDialog.
     * @param path The root directory from where to start the FileDialog
     * @throws NotDirectoryException The path is not a directory
     */
    void setRootDirectory(String path) throws NotDirectoryException;
}
