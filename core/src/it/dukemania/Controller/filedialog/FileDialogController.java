package it.dukemania.Controller.filedialog;

import java.nio.file.NotDirectoryException;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public interface FileDialogController {
    void setFilter(String filter) throws PatternSyntaxException;
    void stepBackFromCurrentDirectory();
    List<FileInfo> getFilesInCurrentDirectory();
    String getDirectorySeparator();
    void returnToRootDirectory();
    boolean advanceInDirectory(String directoryName);
    void setSelectedFileListener(FileSelectedListener listener);
    void setRootDirectory(String path) throws NotDirectoryException;
}
