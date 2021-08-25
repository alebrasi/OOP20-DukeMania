package it.dukemania.View.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import it.dukemania.Controller.filedialog.DialogResult;
import it.dukemania.Controller.filedialog.FileDialogController;
import it.dukemania.Controller.filedialog.FileDialogControllerImpl;
import it.dukemania.Controller.filedialog.FileDialogResultListener;
import it.dukemania.Controller.filedialog.FileType;

import java.nio.file.NotDirectoryException;


public class FileDialog extends Dialog {
    private final Skin skin;
    private final String title;
    private Table tblFiles;
    private TextField txtSelectedSong;
    private final FileDialogController controller;
    private String selectedFileName;
    private String selectedFilePath;

    private FileDialogResultListener result;

    public FileDialog(final String title, final Skin skin) {
        super("", skin);
        this.skin = skin;
        this.title = title;
        controller = new FileDialogControllerImpl();
        populateDialog();
    }

    private void populateDialog() {
        Table tblButtons = this.getButtonTable();
        tblFiles = new Table();
        txtSelectedSong = new TextField("", skin);
        txtSelectedSong.setDisabled(true);
        TextButton btnBack = new TextButton("Back", skin);
        Table tblTitleDialog = this.getTitleTable();
        Table tblContent = this.getContentTable();
        ScrollPane tableFilesScroller = new ScrollPane(tblFiles);
        TextButton btnOK = new TextButton("OK", this.skin);
        TextButton btnCancel = new TextButton("Cancel", this.skin);

        tblTitleDialog.clearChildren();
        tblTitleDialog.add(new Label(this.title, this.skin)).padTop(200).row();
        tblTitleDialog.add(btnBack).expand().left().padBottom(60);
        Dialog thisDialog = this;

        btnBack.addListener(new ClickListener() {
            public void clicked(final InputEvent event, final float x, final float y) {
                controller.stepBackFromCurrentDirectory();
                updateFilesTable();
            }
        });

        controller.setSelectedFileListener((fileName, filePath) -> {
            this.selectedFileName = fileName;
            this.selectedFilePath = filePath;
            txtSelectedSong.setText(fileName);
        });

        btnCancel.addListener(new ClickListener() {
           @Override
           public void clicked(final InputEvent event, final float x, final float y) {
               if (result != null) {
                   result.result(DialogResult.CANCEL, "", "");
               }
               thisDialog.hide();
           }
        });

        btnOK.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                if (result != null) {
                    result.result(DialogResult.OK, selectedFileName, selectedFilePath);
                }
                thisDialog.hide();
            }
        });

        tblFiles.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                Label lblFile = (Label) event.getTarget();
                if (controller.advanceInDirectory(lblFile.getText().toString())) {
                    updateFilesTable();
                }
            }
        });

        tblButtons.add(new Label("Selected track: ", skin)).fill();
        tblButtons.add(txtSelectedSong).expand().fillX();
        tblButtons.row();
        tblButtons.add(btnOK);
        tblButtons.add(btnCancel);
        tblContent.add(tableFilesScroller).expand().fillX().left().padTop(140).padBottom(20);
    }

    private void updateFilesTable() {
        tblFiles.clearChildren();
        controller.getFilesInCurrentDirectory()
                .forEach(file -> {
                    String pathSeparator = this.controller.getDirectorySeparator();
                    String formattedFileStr = file.getFileName() + ((file.getType() == FileType.DIRECTORY) ? pathSeparator : "");
                    Label lblFile = new Label(formattedFileStr, skin);
                    tblFiles.add(lblFile).expand().fillX().left().row();
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog show(final Stage stage) {
        controller.returnToRootDirectory();
        updateFilesTable();
        return super.show(stage);
    }

    /**
     * {@inheritDoc}
     */
    public void setResultListener(final FileDialogResultListener result) {
        this.result = result;
    }

    /**
     * {@inheritDoc}
     */
    public void setFilter(final String regex) {
        controller.setFilter(regex);
    }

    /**
     * {@inheritDoc}
     */
    public void setRootDirectory(final String directory) throws NotDirectoryException {
        controller.setRootDirectory(directory);
    }
}
