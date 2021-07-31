package it.dukemania.Controller.filedialog;

public class FileInfo {

    private final String fileName;
    private final FileType type;

    public FileInfo(final String fileName, final FileType type) {
        this.fileName = fileName;
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public FileType getType() {
        return type;
    }
}
