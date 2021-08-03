package it.dukemania.View.menu.old;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import it.dukemania.windowmanager.DukeManiaWindowState;
import it.dukemania.windowmanager.SwitchWindowNotifier;
import it.dukemania.windowmanager.Window;

public class TitleScreen extends ApplicationAdapter implements Window {
    private FreeTypeFontGenerator generator;
    private Stage stage;
    private Stage backStage;
    private Label.LabelStyle lblStyle;
    private Image backgroundImage;
    private TextButton.TextButtonStyle txtBtnStyle;
    private Skin skin;
    private Camera camera;

    private SwitchWindowNotifier listener;

    @Override
    public void create() {
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

        TextButton btnPlay = new TextButton("PLAY", skin);
        TextButton btnQuit = new TextButton("QUIT", skin);
        TextButton btnOptions = new TextButton("OPTIONS", skin);

        btnOptions.pack();
        btnPlay.pack();
        btnQuit.pack();

        btnPlay.setTransform(true);
        btnQuit.setTransform(true);
        btnOptions.setTransform(true);

        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                System.out.println(listener);
                if (listener != null) {
                    System.out.println("Play");
                    listener.switchWindow(DukeManiaWindowState.PLAY);
                }
            }
        });

        btnOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Options");
            }
        });

        btnQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Quit");
                Gdx.app.exit();
                dispose();
                System.exit(0);
            }
        });

		/*
		lblStyle.font = skin.getFont("title");
		lblStyle.fontColor = Color.BLACK;

		Label nameLabel = new Label("", lblStyle);
		TextField nameText = new TextField("", skin);
		Label addressLabel = new Label("Address:", skin);
		TextField addressText = new TextField("", skin);
		*/

        Container<Table> mainMenuContainer = new Container<>();
        Table table = new Table(skin);

        table.setDebug(false);
        table.add(btnPlay);
        table.row();
        table.add(btnOptions);
        table.row();
        table.add(btnQuit);

        table.getCells().forEach(s -> s.padTop(buttonPadding));
        mainMenuContainer.setActor(table);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - byPercent(screenHeight, 0.12f));

        backStage.addActor(backgroundImage);
        stage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(stage);
    }

    private float byPercent(float size, float percent) {
        return size * percent;
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("Resize called " + width + " " + height);
        //skin.getFont("title").getData().setScale(width * 0.002f, height * 0.002f);
        stage.getViewport().update(width, height);
        backStage.getViewport().update(width, height);
        backStage.getViewport().apply();
        stage.getViewport().apply();
    }

    @Override
    public void render() {
        //Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backStage.act();
        stage.act();

        backStage.draw();
        stage.draw();
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

