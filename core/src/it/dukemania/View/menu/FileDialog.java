package it.dukemania.View.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class FileDialog extends Dialog {

    enum DialogResult {
        OK, CANCEL
    }

    public interface ResultListener {
        void result(DialogResult dialogResult, String filePath, String fileName);
    }

    private Pattern pattern;
    private final Skin skin;
    private final String title;
    private String selectedFile;
    private TextButton btnOK;
    private TextButton btnCancel;
    private String separator;
    private String currentDir;
    private String homeDir;
    private Table tblFiles;
    private TextField txtSelectedSong;

    private ResultListener result;

    public FileDialog(final String title, final Skin skin) {
        super("", skin);
        this.skin = skin;
        this.title = title;
        this.selectedFile = "";
        this.separator = File.separator;
        this.homeDir = System.getProperty("user.home") + separator;
        this.currentDir = this.homeDir;
        String regex = String.format("^.+%s\\w+%s", separator, separator);
        pattern = Pattern.compile(regex);
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
        this.btnOK = new TextButton("OK", this.skin);
        this.btnCancel = new TextButton("Cancel", this.skin);

        tblTitleDialog.clearChildren();
        tblTitleDialog.add(new Label(this.title, this.skin)).padTop(200).row();
        tblTitleDialog.add(btnBack).expand().left().padBottom(60);
        Dialog thisDialog = this;

        btnBack.addListener(new ClickListener() {
            public void clicked(final InputEvent event, final float x, final float y) {
                Matcher matcher = pattern.matcher(currentDir.substring(0, currentDir.length() - 1));
                if (matcher.find()) {
                    currentDir = matcher.group();
                    update(currentDir);
                }
            }
        });

        btnCancel.addListener(new ClickListener() {
           @Override
           public void clicked(final InputEvent event, final float x, final float y) {
               result.result(DialogResult.CANCEL, "", "");
               thisDialog.hide();
           }
        });

        btnOK.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                result.result(DialogResult.OK, currentDir, selectedFile);
                thisDialog.hide();
            }
        });

        tblFiles.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                Label a = (Label) event.getTarget();
                if (Files.isDirectory(Paths.get(currentDir + a.getText().toString()))) {
                    currentDir += a.getText().toString();
                    update(currentDir);
                } else {
                    selectedFile = a.getText().toString();
                    txtSelectedSong.setText(selectedFile);
                }
                System.out.println(currentDir);
            }
        });

        tblButtons.add(new Label("Selected track: ", skin)).fill();
        tblButtons.add(txtSelectedSong).expand().fillX();
        tblButtons.row();
        tblButtons.add(btnOK);
        tblButtons.add(btnCancel);
        tblContent.add(tableFilesScroller).expand().fillX().left().padTop(140).padBottom(20);
    }

    private void update(String path) {
        tblFiles.clearChildren();
        try (Stream<Path> paths = Files.walk(Paths.get(path), 1)) {
            paths
                    .skip(1)
                    .filter(s -> {
                        try {
                            return !Files.isHidden(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    })
                    .forEach(f -> {
                        Label lblFIle = new Label(f.getFileName().toString() + (Files.isDirectory(f) ? separator : ""), skin);
                        tblFiles.add(lblFIle).expand().fillX().left().row();
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dialog show(final Stage stage) {
        this.currentDir = homeDir;
        System.out.println(currentDir);
        update(this.currentDir);
        return super.show(stage);
    }

    public void setResultListener(final ResultListener result) {
        this.result = result;
    }
}
