package it.dukemania.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import it.dukemania.controller.filedialog.DialogResult;
import it.dukemania.controller.songselection.SongSelectionWindowController;
import it.dukemania.controller.songselection.SongSelectionWindowControllerImpl;
import it.dukemania.model.serializers.song.SongInfo;
import it.dukemania.view.notesGraphics.AssetsManager;
import it.dukemania.midi.InstrumentType;
import it.dukemania.windowmanager.DukeManiaWindowState;

import javax.sound.midi.InvalidMidiDataException;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SongSelectionWindow extends AbstractView {

    private SongSelectionWindowController controller;
    private static final int FILE_DIALOG_SIZE = 800;
    private static final int TABLE_CONFIGS_OFFSET_Y = -300;
    private static final int TABLE_PADDING = 30;
    private static final float TABLE_BACKGROUND_OPACITY = 0.3f;
    private static final float TABLE_TRACKS_HEIGHT = 200f;

    public SongSelectionWindow(final String backgroundPath, final Skin skin) throws NoSuchAlgorithmException {
        super(backgroundPath, skin);
    }

    @Override
    public final void create() {
        super.create();
        Texture tableBackgroundTexture = AssetsManager.getInstance().getTexture("background.png");
        try {
            controller = new SongSelectionWindowControllerImpl(switchWindowNotifier, data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        float screenWidth = mainStage.getWidth();
        float screenHeight = mainStage.getHeight();

        Container<Table> mainMenuContainer = new Container<>();
        Table tblConfigSong = new Table(skin);
        Table tblTracks = new Table();

        TextButton btnSongSelect = new TextButton("Select song", skin);
        Label lblFileName = new Label("", skin);
        Label lblConfig = new Label("Configure Song", skin);
        Label lblTrackName = new Label("Track Name", skin);
        Label lblInstruments = new Label("Instrument", skin);
        Label lblChannel = new Label("Channel", skin);
        Label lblSongName = new Label("", skin);
        Label lblBPM = new Label("", skin);
        Label lblDifficultyLevel = new Label("Difficulty", skin);
        Label lblNumCols = new Label("Number of columns", skin);
        TextButton btnBackToTitle = new TextButton("Back to title screen", skin);
        TextButton btnSaveSongConfigs = new TextButton("Save configuration", skin);
        TextButton btnPlayTrack = new TextButton("Play Track", skin);
        SelectBox<Integer> slctNumCols = new SelectBox<>(skin);
        slctNumCols.setItems(controller.getNumOfCols());

        btnPlayTrack.setTouchable(Touchable.disabled);
        btnSaveSongConfigs.setTouchable(Touchable.disabled);

        ScrollPane scrollTableTracks = new ScrollPane(tblTracks, skin);
        scrollTableTracks.setFadeScrollBars(false);
        scrollTableTracks.setScrollingDisabled(true, false);

        FileDialog fd = new FileDialog("Select song", skin);
        try {
            fd.setRootDirectory(controller.getMidiDirectory());
        } catch (NotDirectoryException e) {
            e.printStackTrace();
        }
        fd.setFilter("\\\\*.mid");
        ButtonGroup<CheckBox> playableTracks = new ButtonGroup<>();

        fd.setResultListener((res, fileName, filePath) -> {
            if (res.equals(DialogResult.OK)) {
                playableTracks.clear();
                playableTracks.setMinCheckCount(1);
                playableTracks.setMaxCheckCount(1);
                boolean isACorrectMidiFile = true;
                try {
                    controller.setSongPath(filePath);
                    btnPlayTrack.setTouchable(Touchable.enabled);
                    btnSaveSongConfigs.setTouchable(Touchable.enabled);
                } catch (InvalidMidiDataException e) {
                    new ErrorDialog("The selected file is not a valid MIDI", skin).show(mainStage);
                    e.printStackTrace();
                    isACorrectMidiFile = false;
                } catch (IOException e) {
                    new ErrorDialog("Could not read the selected file", skin).show(mainStage);
                    e.printStackTrace();
                    isACorrectMidiFile = false;
                }
                if (isACorrectMidiFile) {
                    String[] availableInstruments = controller.getAllInstruments();

                    SongInfo selectedSong = controller.getSongInfo();
                    lblFileName.setText(fileName);
                    lblSongName.setText("Song name: " + selectedSong.getTitle());
                    lblBPM.setText("BPM: " + selectedSong.getBPM());
                    lblSongName.setAlignment(Align.center);
                    lblBPM.setAlignment(Align.center);

                    tblTracks.clearChildren();

                    selectedSong.getTracks().forEach(s -> {
                        SelectBox<String> slctInstruments = new SelectBox<>(skin);
                        slctInstruments.setItems(availableInstruments);
                        slctInstruments.setSelected(s.getInstrument().toString());
                        slctInstruments.setMaxListCount(3);
                        CheckBox ck = new CheckBox(String.valueOf(s.getChannel()), skin);
                        playableTracks.add(ck);
                        tblTracks.add(ck).uniformX().fillX().padLeft(TABLE_PADDING);
                        TextField txtTrackName = new TextField(s.getTrackName(), skin);
                        tblTracks.add(txtTrackName).uniformX().fillX().padRight(TABLE_PADDING);
                        tblTracks.add(new Label(s.getDifficultyLevel().getEffectiveName(), skin)).fillX().uniformX();
                        tblTracks.add(slctInstruments).fillX().uniformX().right().padRight(TABLE_PADDING);
                        tblTracks.row();
                    });
                }
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
                switchWindowNotifier.switchWindow(DukeManiaWindowState.TITLE, data);
            }
        });

        btnPlayTrack.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                if (playableTracks.getButtons().size != 0) {
                    controller.setPlayTrack(Integer.parseInt(playableTracks.getChecked().getText().toString()));
                }
                controller.setColumnsNumber(slctNumCols.getSelected());

                try {
                    controller.playSong();
                } catch (InvalidMidiDataException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSaveSongConfigs.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                List<String> trackNames = Arrays.stream(tblTracks.getChildren().toArray())
                                                            .filter(l -> l.getClass().equals(TextField.class))
                                                            .map(f -> ((TextField) f).getText())
                                                            .collect(Collectors.toList());

                //Disabled unchecked warning for line 128. Is always a SelectBox<String>
                @SuppressWarnings("unchecked")
                List<InstrumentType> instruments = Arrays.stream(tblTracks.getChildren().toArray())
                                                            .filter(l -> l.getClass().equals(SelectBox.class))
                                                            .map(f -> ((SelectBox<String>) f).getSelected())
                                                            .map(InstrumentType::valueOf)
                                                            .collect(Collectors.toList());
                controller.updateTracks(trackNames, instruments);
            }
        });

        //Sets the layout of the song table configuration
        tblConfigSong.add(btnSongSelect);
        tblConfigSong.add(lblFileName).colspan(4).left();
        tblConfigSong.row();
        tblConfigSong.add(lblBPM).expand().fillX().colspan(4);
        tblConfigSong.row();
        tblConfigSong.add(lblConfig).colspan(4).expand().center().padBottom(TABLE_PADDING);
        tblConfigSong.row();
        tblConfigSong.add(lblChannel).expandX().uniformX().padLeft(TABLE_PADDING);
        tblConfigSong.add(lblTrackName).expandX().uniformX();
        tblConfigSong.add(lblDifficultyLevel).expandX().uniformX();
        tblConfigSong.add(lblInstruments).expandX().uniformX().padRight(TABLE_PADDING);
        tblConfigSong.row();
        tblConfigSong.add(scrollTableTracks).expandX().fillX().colspan(4).height(TABLE_TRACKS_HEIGHT);
        tblConfigSong.row();
        tblConfigSong.add(lblNumCols).padLeft(TABLE_PADDING).padTop(TABLE_PADDING);
        tblConfigSong.add(slctNumCols).padTop(TABLE_PADDING);
        tblConfigSong.row();
        tblConfigSong.add(btnBackToTitle).expandX().left().padLeft(TABLE_PADDING);
        tblConfigSong.add(btnSaveSongConfigs).expandX().colspan(3).right().padRight(TABLE_PADDING);
        tblConfigSong.row();
        tblConfigSong.add(btnPlayTrack).expandX().colspan(4).fillX();

        NinePatch patch = new NinePatch(tableBackgroundTexture, 0, 0, 0, 0);
        NinePatchDrawable drawablePatch = new NinePatchDrawable(patch);
        patch.setColor(new Color(1, 1, 1, TABLE_BACKGROUND_OPACITY));
        tblConfigSong.background(drawablePatch);

        mainMenuContainer.setActor(tblConfigSong);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) + TABLE_CONFIGS_OFFSET_Y);
        mainStage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(mainStage);
    }
}
