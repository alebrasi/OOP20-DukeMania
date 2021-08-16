package it.dukemania.View.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import it.dukemania.Controller.filedialog.DialogResult;
import it.dukemania.Controller.songselection.SongSelectionWindowController;
import it.dukemania.Controller.songselection.SongSelectionWindowControllerImpl;
import it.dukemania.View.AbstractView;
import it.dukemania.windowmanager.DukeManiaWindowState;

import java.security.NoSuchAlgorithmException;

public class SongSelectionWindow extends AbstractView {

    private final SongSelectionWindowController controller = new SongSelectionWindowControllerImpl();
    private final int FILE_DIALOG_SIZE = 800;
    private final int TABLE_CONFIGS_OFFSET_Y = -300;

    public SongSelectionWindow(final String backgroundPath, final Skin skin) throws NoSuchAlgorithmException {
        super(backgroundPath, skin);
    }

    @Override
    public void create() {
        super.create();

        float screenWidth = mainStage.getWidth();
        float screenHeight = mainStage.getHeight();

        Container<Table> mainMenuContainer = new Container<>();
        Table tblConfigSong = new Table(skin);
        Table tblTracks = new Table();

        TextButton btnSongSelect = new TextButton("Select song", skin);
        Label lblSongName = new Label("Darude sandstorm.mid", skin);
        Label lblConfig = new Label("Configure Song", skin);
        Label lblTrackName = new Label("Track Name", skin);
        Label lblInstruments = new Label("Instrument", skin);
        Label lblTrackID = new Label("Track ID", skin);
        TextButton btnBackToTitle = new TextButton("Back to title screen", skin);
        TextButton btnSaveSongConfigs = new TextButton("Save configuration", skin);

        //TODO Fix ScrollPane vertical scroll (overflowing from screen)
        ScrollPane scrollTableTracks = new ScrollPane(tblTracks, skin);
        scrollTableTracks.setScrollingDisabled(true, false);

        FileDialog fd = new FileDialog("Select song", skin);
        fd.setResultListener((res, fileName, filePath) -> {
            if (res.equals(DialogResult.OK)) {
                lblSongName.setText(fileName);

                ButtonGroup<CheckBox> playableTracks = new ButtonGroup<>();
                playableTracks.setMinCheckCount(1);
                playableTracks.setMaxCheckCount(1);
                controller.openSong(filePath);
                String[] ins = controller.getAllInstruments();

                tblTracks.clearChildren();
                controller.getTracks().forEach(s -> {
                    //TODO Fix height of SelectBox
                    SelectBox<String> slctInstruments = new SelectBox<>(skin);
                    slctInstruments.setItems(ins);
                    slctInstruments.setSelected(s.getInstrumentName());
                    slctInstruments.setMaxListCount(3);
                    CheckBox ck = new CheckBox(String.valueOf(s.getTrackID()), skin);
                    playableTracks.add(ck);
                    tblTracks.add(ck).expand().align(Align.center);
                    tblTracks.add(new Label(s.getTrackName(), skin)).align(Align.left);
                    tblTracks.add(slctInstruments).expand().align(Align.right);
                    tblTracks.row();
                });
                tblConfigSong.getCell(scrollTableTracks).width(tblTracks.getWidth() + 100);
            }
        });

        btnSongSelect.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                fd.show(mainStage);
                //fd.setBounds(screenWidth / 2 - 300, screenHeight / 2 - 100, 800, 800);
                fd.setSize(FILE_DIALOG_SIZE, FILE_DIALOG_SIZE);
            }
        });

        btnBackToTitle.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                switchWindowNotifier.switchWindow(DukeManiaWindowState.TITLE);
            }
        });

        scrollTableTracks.setFadeScrollBars(false);
        scrollTableTracks.setScrollingDisabled(true, false);

        //TODO Fix table config song layout
        tblConfigSong.add(btnSongSelect);
        tblConfigSong.add(lblSongName).padLeft(100).padRight(30).colspan(2);
        tblConfigSong.row();
        tblConfigSong.add(lblConfig).padTop(25).padBottom(10).colspan(3);
        tblConfigSong.row();
        tblConfigSong.add(lblTrackID).expand().align(Align.left).padLeft(20);
        tblConfigSong.add(lblTrackName).expand().align(Align.center);
        tblConfigSong.add(lblInstruments).expand().align(Align.right).padRight(20);
        tblConfigSong.row();
        tblConfigSong.add(scrollTableTracks).expand().fillX().colspan(3).padTop(20).padBottom(20).height(200);
        tblConfigSong.row();
        tblConfigSong.add(btnBackToTitle).padLeft(20);
        tblConfigSong.add(btnSaveSongConfigs).expand().align(Align.right);

        //TODO Dispose texture
        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("background.png")), 0, 0, 0, 0);
        NinePatchDrawable sas = new NinePatchDrawable(patch);
        patch.setColor(new Color(1, 1, 1, 0.2f));
        tblConfigSong.background(sas);

        mainMenuContainer.setActor(tblConfigSong);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) + TABLE_CONFIGS_OFFSET_Y);

        mainStage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(mainStage);
    }
}
