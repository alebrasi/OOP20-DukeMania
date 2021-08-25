package it.dukemania.controller.filedialog;

public interface FileSelectedListener {
    /**
     * Detects when a file selected.
     * @param fileName Name of the file
     * @param filePath Path of the file
     */
    void selectedFile(String fileName, String filePath);
}
