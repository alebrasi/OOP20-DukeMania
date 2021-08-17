package it.dukemania.View.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import it.dukemania.Controller.filedialog.DialogResult;
import it.dukemania.Controller.songselection.SongSelectionWindowController;
import it.dukemania.Controller.songselection.SongSelectionWindowControllerImpl;
import it.dukemania.View.AbstractView;
import it.dukemania.midi.InstrumentType;
import it.dukemania.windowmanager.DukeManiaWindowState;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SongSelectionWindow extends AbstractView {

    private final SongSelectionWindowController controller = new SongSelectionWindowControllerImpl();
    private final int FILE_DIALOG_SIZE = 800;
    private final int TABLE_CONFIGS_OFFSET_Y = -300;
    private final int TABLE_PADDING = 30;
    private final float TABLE_BACKGROUND_OPACITY = 0.2f;
    private final float TABLE_TRACKS_HEIGHT = 200f;

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
        Label lblSongName = new Label("", skin);
        Label lblConfig = new Label("Configure Song", skin);
        Label lblTrackName = new Label("Track Name", skin);
        Label lblInstruments = new Label("Instrument", skin);
        Label lblChannel = new Label("Channel", skin);
        TextButton btnBackToTitle = new TextButton("Back to title screen", skin);
        TextButton btnSaveSongConfigs = new TextButton("Save configuration", skin);
        TextButton btnPlayTrack = new TextButton("Play Track", skin);

        //TODO Fix ScrollPane vertical scroll (overflowing from screen)
        ScrollPane scrollTableTracks = new ScrollPane(tblTracks, skin);
        scrollTableTracks.setFadeScrollBars(false);
        scrollTableTracks.setScrollingDisabled(true, false);

        FileDialog fd = new FileDialog("Select song", skin);
        ButtonGroup<CheckBox> playableTracks = new ButtonGroup<>();

        fd.setResultListener((res, fileName, filePath) -> {
            if (res.equals(DialogResult.OK)) {
                lblSongName.setText(fileName);
                playableTracks.clear();
                playableTracks.setMinCheckCount(1);
                playableTracks.setMaxCheckCount(1);
                controller.openSong(filePath);
                String[] availableInstruments = controller.getAllInstruments();

                tblTracks.clearChildren();
                controller.getTracks().forEach(s -> {
                    SelectBox<String> slctInstruments = new SelectBox<>(skin);
                    slctInstruments.setItems(availableInstruments);
                    slctInstruments.setSelected(s.getInstrument().toString());
                    slctInstruments.setMaxListCount(3);
                    CheckBox ck = new CheckBox(String.valueOf(s.getChannel()), skin);
                    playableTracks.add(ck);
                    tblTracks.add(ck).uniformX().padLeft(TABLE_PADDING);
                    TextField txtTrackName = new TextField(s.getTrackName(), skin);
                    tblTracks.add(txtTrackName).expand().fillX().uniformX().padRight(TABLE_PADDING);
                    tblTracks.add(slctInstruments).uniformX().right().padRight(TABLE_PADDING);
                    tblTracks.row();
                });
                tblTracks.pack();
                tblConfigSong.getCell(scrollTableTracks).width(tblTracks.getWidth() + 100);
            }
        });

        btnSongSelect.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                fd.show(mainStage);
                fd.setSize(FILE_DIALOG_SIZE, FILE_DIALOG_SIZE);
            }
        });

        btnBackToTitle.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                switchWindowNotifier.switchWindow(DukeManiaWindowState.TITLE);
            }
        });

        btnPlayTrack.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                controller.setPlayTrack(playableTracks.getCheckedIndex());
                List<String> trackNames = Arrays.stream(tblTracks.getChildren().toArray())
                                                            .filter(l -> l.getClass().equals(TextField.class))
                                                            .map(f -> ((TextField) f).getText())
                                                            .collect(Collectors.toList());
                List<InstrumentType> instruments = Arrays.stream(tblTracks.getChildren().toArray())
                                                            .filter(l -> l.getClass().equals(SelectBox.class))
                                                            .map(f -> (SelectBox<String>)f)
                                                            .map(f -> InstrumentType.valueOf(f.getSelected()))
                                                            .collect(Collectors.toList());
                controller.updateTracks(trackNames, instruments);
                //switchWindowNotifier.switchWindow(DukeManiaWindowState.PLAY);
            }
        });

        //Sets the layout of the song table configuration
        tblConfigSong.add(btnSongSelect);
        tblConfigSong.add(lblSongName).expand().fillX();
        tblConfigSong.row();
        tblConfigSong.add(lblConfig).colspan(3).expand().center().padBottom(TABLE_PADDING);
        tblConfigSong.row();
        tblConfigSong.add(lblChannel).expandX().uniformX().padLeft(TABLE_PADDING);
        tblConfigSong.add(lblTrackName).expandX().uniformX();
        tblConfigSong.add(lblInstruments).expandX().uniformX().padRight(TABLE_PADDING);
        tblConfigSong.row();
        tblConfigSong.add(scrollTableTracks).expand().fillX().colspan(3).height(TABLE_TRACKS_HEIGHT);
        tblConfigSong.row();
        tblConfigSong.add(btnBackToTitle).expandX().left().padLeft(TABLE_PADDING);
        tblConfigSong.add(btnSaveSongConfigs).expandX().colspan(2).right().padRight(TABLE_PADDING);
        tblConfigSong.row();
        tblConfigSong.add(btnPlayTrack).expandX().colspan(3).fillX();

        //TODO Dispose texture and rename file
        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("background.png")), 0, 0, 0, 0);
        NinePatchDrawable sas = new NinePatchDrawable(patch);
        patch.setColor(new Color(1, 1, 1, TABLE_BACKGROUND_OPACITY));
        tblConfigSong.background(sas);

        mainMenuContainer.setActor(tblConfigSong);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) + TABLE_CONFIGS_OFFSET_Y);
        //mainMenuContainer.setDebug(true, true);
        mainStage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(mainStage);
    }
}
