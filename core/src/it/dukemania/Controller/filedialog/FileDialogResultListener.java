package it.dukemania.Controller.filedialog;

public interface FileDialogResultListener {
    /**
     * Detects when the file dialog produce a result.
     * @param res The result of the file dialog
     * @param fileName The selected file name
     * @param filePath The selected file path
     */
    void result(DialogResult res, String fileName, String filePath);
}
