package it.dukemania.Controller.filedialog;

import java.nio.file.NotDirectoryException;
import java.util.List;

public interface FileDialogController {
    void setFilter(String filter);
    void stepBackFromCurrentDirectory();
    List<FileInfo> getFilesInCurrentPath();
    void advanceInDirectory(String directoryName);
    void setResultListener(FileDialogResult listener);
    void setRootDirectory(String path) throws NotDirectoryException;
}
