package it.dukemania.View.menu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import sun.jvm.hotspot.debugger.cdbg.basic.LazyBlockSym;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class PlayScreen extends ApplicationAdapter {
    private FreeTypeFontGenerator generator;
    private Stage stage;
    private Stage backStage;
    private Label.LabelStyle lblStyle;
    private Image backgroundImage;
    private TextButton.TextButtonStyle txtBtnStyle;
    private Skin skin;
    private Camera camera;

    @Override
    public void create () {

        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track("asd", "lol"));
        tracks.add(new Track("sasas", "sdfsdf"));
        tracks.add(new Track("sahghs", "sdujg"));
        tracks.add(new Track("sdfgdf", "sdfghd"));
        tracks.add(new Track("sdfasfg", "ssrfsadf"));

        //---------------------------------------------------------------------------------------------
        String TEXTURE_PATH = "textures/quantum-horizon/quantum-horizon-ui.json";
        String MENU_BACKGROUND_IMAGE_PATH = "DukeMania.png";
        String FONT_PATH = "fonts/agency-fb.ttf";

        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        int fontSize = 50;
        float fontBorderWidth = 0.5f;
        Color fontColor = Color.BLACK;
        float buttonPadding = 20f;

        Texture img = new Texture(MENU_BACKGROUND_IMAGE_PATH);
        backgroundImage = new Image(img);

        camera = new OrthographicCamera(1920, 1080);
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

		/*
		txtBtnStyle = new TextButton.TextButtonStyle();
		lblStyle = new Label.LabelStyle();
		*/

        //--------------------------------------------------------------------------------------------------------------

		/*
		lblStyle.font = skin.getFont("title");
		lblStyle.fontColor = Color.BLACK;

		Label nameLabel = new Label("", lblStyle);
		TextField nameText = new TextField("", skin);
		Label addressLabel = new Label("Address:", skin);
		TextField addressText = new TextField("", skin);
		*/

        Container<Table> mainMenuContainer = new Container<>();
        //mainMenuContainer.setDebug(true);
        Table tblConfigSong = new Table(skin);
        Table tblTracks = new Table();

        tracks.forEach(s -> {
            System.out.println(s.trackName);
            tblTracks.add(new Label(s.trackName, skin)).padRight(100);
            tblTracks.add(new TextButton(s.instrumentName, skin)).padRight(100);
            tblTracks.add(new Label("fffasdad", skin));
            tblTracks.row();
        });


        ScrollPane scrl = new ScrollPane(tblTracks , skin);
        //table.debug();

        tblConfigSong.right().top();

        SelectBox<String> instruments = new SelectBox<>(skin);

        instruments.setItems("Bass", "Drum", "Synth", "Amoung", "Amoung Pequeno");

        TextButton txtSongSelect = new TextButton("Select song", skin);
        Label lblSongName = new Label("Darude sandstorm.mid", skin);
        Label lblConfig = new Label("Configure Song", skin);
        Label lblTrackName = new Label("Track", skin);
        Label lblPlayTrack = new Label("Play Track", skin);
        Label lblInstruments = new Label("Instrument", skin);


        tblConfigSong.add(txtSongSelect);
        tblConfigSong.add(lblSongName).padLeft(100).colspan(2);
        tblConfigSong.row();
        tblConfigSong.add(lblConfig).padTop(25).padBottom(10).colspan(3);
        tblConfigSong.row();
        tblConfigSong.add(lblTrackName);
        tblConfigSong.add(lblPlayTrack);
        tblConfigSong.add(lblInstruments);
        tblConfigSong.row();
        tblConfigSong.add(scrl).height(200).fill().expand().colspan(3).padTop(20);

        mainMenuContainer.fill();
        mainMenuContainer.setActor(tblConfigSong);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - byPercent(screenHeight, 0.12f));

        backStage.addActor(backgroundImage);
        stage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(stage);
    }

    private Table createTrackTable(List<Track> tracks, Skin skin) {
        Table t = new Table();
        ButtonGroup btgTrackName = new ButtonGroup();
        ButtonGroup btgSelectTrack = new ButtonGroup();
        btgTrackName.setMaxCheckCount(1);
        btgTrackName.setMinCheckCount(1);

        tracks.forEach(s -> {
            t.add(new Label(s.trackName, skin));
            t.add(new Button());
            t.add(new Label("dfdfssdf", skin));
            t.row();
        });
        return t;
    }

    private float byPercent(float size, float percent) {
        return size * percent;
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("Resize called " + width + " " + height);
        stage.getViewport().update(width, height);
        //skin.getFont("title").getData().setScale(width * 0.002f, height * 0.002f);
        backStage.getViewport().update(width, height, true);
        backStage.getViewport().apply();
        stage.getViewport().apply();
    }

    @Override
    public void render () {
        //Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backStage.act();
        stage.act();

        backStage.draw();
        stage.draw();
        //stage.setDebugAll(true);
    }

    @Override
    public void dispose () {
        stage.dispose();
        generator.dispose();
        System.out.println("Disposed");
        //img.dispose();
    }
}


