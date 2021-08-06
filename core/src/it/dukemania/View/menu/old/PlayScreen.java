package it.dukemania.View.menu.old;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import it.dukemania.Controller.filedialog.DialogResult;
import it.dukemania.View.menu.FileDialog;
import it.dukemania.View.menu.Track;
import it.dukemania.windowmanager.SwitchWindowNotifier;
import it.dukemania.windowmanager.Window;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends ApplicationAdapter implements Window {
    private FreeTypeFontGenerator generator;
    private Stage stage;
    private Stage backStage;
    private Label.LabelStyle lblStyle;
    private Image backgroundImage;
    private TextButton.TextButtonStyle txtBtnStyle;
    private Skin skin;
    private Camera camera;

    private final boolean userCanSelectInstrument = false;
    private SwitchWindowNotifier listener = null;

    @Override
    public void create() {

        List<Track> tracks = new ArrayList<>();
        /*
        tracks.add(new Track("asd", "lol"));
        tracks.add(new Track("sasas", "sdfsdfasdasdasdasdad"));
        tracks.add(new Track("sahghs", "sdujg"));
        tracks.add(new Track("sdfgdf", "sdfghd"));
        tracks.add(new Track("sdfasfg", "ssrfsadf"));
        tracks.add(new Track("sdfasfg", "ssrfsadf"));
        tracks.add(new Track("sdffhsjdfhasdag", "ssrfsadfsdsfdsdf"));
        tracks.add(new Track("sdfasfg", "ssrfsadf"));
        tracks.add(new Track("sdfasfg", "ssrfsadf"));

         */

        //---------------------------------------------------------------------------------------------
        String TEXTURE_PATH = "textures/quantum-horizon/quantum-horizon-ui.json";
        String MENU_BACKGROUND_IMAGE_PATH = "DukeMania.png";
        String FONT_PATH = "fonts/agency-fb.ttf";

        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        final int fontSize = 50;
        float fontBorderWidth = 0.5f;
        Color fontColor = Color.BLACK;

        Texture img = new Texture(MENU_BACKGROUND_IMAGE_PATH);
        backgroundImage = new Image(img);

        camera = new OrthographicCamera(2560, 1440);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        stage = new Stage(new ExtendViewport(camera.viewportWidth, camera.viewportHeight, camera));
        backStage = new Stage(new StretchViewport(backgroundImage.getWidth(), backgroundImage.getHeight()));

        float screenWidth = stage.getWidth();
        float screenHeight = stage.getHeight();

        generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.borderWidth = fontBorderWidth;
        parameter.color = fontColor;
        BitmapFont menuFont = generator.generateFont(parameter);
        menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        skin = new Skin();
        skin.add("font", menuFont);
        skin.add("title", menuFont);
        skin.addRegions(new TextureAtlas(Gdx.files.internal("textures/quantum-horizon/quantum-horizon-ui.atlas")));
        skin.load(Gdx.files.internal(TEXTURE_PATH));

        //--------------------------------------------------------------------------------------------------------------

        Container<Table> mainMenuContainer = new Container<>();
        Table tblConfigSong = new Table(skin);
        Table tblTracks = new Table();

        TextButton btnSongSelect = new TextButton("Select song", skin);
        Label lblSongName = new Label("Darude sandstorm.mid", skin);
        Label lblConfig = new Label("Configure Song", skin);
        Label lblTrackName = new Label("Track", skin);
        Label lblInstruments = new Label("Instrument", skin);


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
                    CheckBox ck = new CheckBox(s.getTrackName(), skin);
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
            public void clicked(InputEvent event, float x, float y) {
                fd.show(stage);
                fd.setBounds(screenWidth / 2 - 300, screenHeight / 2 - 100, 800, 800);
                fd.setSize(800, 800);
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


        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("background.png")), 0, 0, 0, 0);
        NinePatchDrawable sas = new NinePatchDrawable(patch);
        patch.setColor(new Color(1, 1, 1, 0.2f));
        tblConfigSong.background(sas);

        mainMenuContainer.setActor(tblConfigSong);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - byPercent(screenHeight, 0.12f));

        backStage.addActor(backgroundImage);
        stage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(stage);
    }

    private float byPercent(final float size, final float percent) {
        return size * percent;
    }

    @Override
    public void resize(final int width, final int height) {
        System.out.println("Resize called " + width + " " + height);
        stage.getViewport().update(width, height);
        //skin.getFont("title").getData().setScale(width * 0.002f, height * 0.002f);
        //backStage.getViewport().update(width, height, true);
        backStage.getViewport().update(width, height);
        backStage.getViewport().apply();
        stage.getViewport().apply();
    }

    @Override
    public void render() {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backStage.act();
        stage.act();

        backStage.draw();
        stage.draw();
        //stage.setDebugAll(true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        generator.dispose();
        System.out.println("Disposed");
        //img.dispose();
    }

    @Override
    public void setWindowListener(final SwitchWindowNotifier listener) {
        this.listener = listener;
    }

}


