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
import it.dukemania.View.AbstractView;
import it.dukemania.windowmanager.DukeManiaWindowState;

import java.util.ArrayList;
import java.util.List;

public class PlayWindow extends AbstractView {

    public PlayWindow(final String backgroundPath, final Skin skin) {
        super(backgroundPath, skin);
    }

    @Override
    public void create() {
        super.create();

        float screenWidth = mainStage.getWidth();
        float screenHeight = mainStage.getHeight();

        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track("asd", "lol"));
        tracks.add(new Track("sasas", "sdfsdfasdasdasdasdad"));
        tracks.add(new Track("sahghs", "sdujg"));
        tracks.add(new Track("sdfgdf", "sdfghd"));
        tracks.add(new Track("sdfasfg", "ssrfsadf"));
        tracks.add(new Track("sdfasfg", "ssrfsadf"));
        tracks.add(new Track("sdffhsjdfhasdag", "ssrfsadfsdsfdsdf"));
        tracks.add(new Track("sdfasfg", "ssrfsadf"));
        tracks.add(new Track("sdfasfg", "ssrfsadf"));

        Container<Table> mainMenuContainer = new Container<>();
        Table tblConfigSong = new Table(skin);
        Table tblTracks = new Table();

        TextButton btnSongSelect = new TextButton("Select song", skin);
        Label lblSongName = new Label("Darude sandstorm.mid", skin);
        Label lblConfig = new Label("Configure Song", skin);
        Label lblTrackName = new Label("Track", skin);
        Label lblInstruments = new Label("Instrument", skin);
        TextButton btnBackToTitle = new TextButton("Back to title screen", skin);


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
                String[] ins = {"Bass", "Drum", "Synth", "", "Guitar"};

                tblTracks.clearChildren();
                tracks.forEach(s -> {
                    //TODO Fix height of SelectBox
                    SelectBox<String> instruments = new SelectBox<>(skin);
                    instruments.setItems(ins);
                    instruments.setMaxListCount(3);
                    CheckBox ck = new CheckBox(s.trackName, skin);
                    playableTracks.add(ck);
                    tblTracks.add(ck).expand().align(Align.left).padRight(30);
                    tblTracks.add(instruments).expand().align(Align.right).padRight(30);
                    tblTracks.row();
                });
                tblConfigSong.getCell(scrollTableTracks).width(tblTracks.getWidth() + 100);
            }
        });

        btnSongSelect.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                fd.show(mainStage);
                fd.setBounds(screenWidth / 2 - 300, screenHeight / 2 - 100, 800, 800);
                fd.setSize(800, 800);
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

        tblConfigSong.add(btnSongSelect);
        tblConfigSong.add(lblSongName).padLeft(100).padRight(30).colspan(2);
        tblConfigSong.row();
        tblConfigSong.add(lblConfig).padTop(25).padBottom(10).colspan(3);
        tblConfigSong.row();
        tblConfigSong.add(lblTrackName).expand().align(Align.left).padLeft(20);
        tblConfigSong.add(lblInstruments).expand().align(Align.right).padRight(20);
        tblConfigSong.row();
        tblConfigSong.add(scrollTableTracks).expand().fillX().colspan(2).padTop(20).padBottom(20).height(200);
        tblConfigSong.row();
        tblConfigSong.add(btnBackToTitle);

        //TODO Dispose texture
        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("background.png")), 0, 0, 0, 0);
        NinePatchDrawable sas = new NinePatchDrawable(patch);
        patch.setColor(new Color(1, 1, 1, 0.2f));
        tblConfigSong.background(sas);

        mainMenuContainer.setActor(tblConfigSong);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - 300);

        mainStage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(mainStage);
    }
}
