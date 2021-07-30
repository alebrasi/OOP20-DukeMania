package it.dukemania.Controller.filedialog;

enum DialogResult {
    OK, CANCEL
}

public interface FileDialogResultListener {
    void result(DialogResult res, String fileName, String filePath);
}
