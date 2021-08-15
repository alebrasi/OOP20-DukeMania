package it.dukemania;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import it.dukemania.View.menu.PlayWindow;
import it.dukemania.View.menu.TitleWindow;
import it.dukemania.windowmanager.DukeManiaWindowState;
import it.dukemania.windowmanager.Window;
import it.dukemania.windowmanager.WindowManager;

import java.security.NoSuchAlgorithmException;

public class DukeManiaTest extends ApplicationAdapter {

    private final WindowManager wdm = new WindowManager();
    private FreeTypeFontGenerator generator;

    private final String ATLAS_PATH = "textures/quantum-horizon/quantum-horizon-ui.atlas";
    private final String TEXTURE_JSON_PATH = "textures/quantum-horizon/quantum-horizon-ui.json";
    private final String MENU_BACKGROUND_IMAGE_PATH = "DukeMania.png";
    private final String FONT_PATH = "fonts/agency-fb.ttf";

    private final int fontSize = 50;
    private float fontBorderWidth = 0.5f;
    private final Color fontColor = Color.BLACK;

    private BitmapFont generateFont() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.borderWidth = fontBorderWidth;
        parameter.color = fontColor;
        BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        return font;
    }

    private Skin createSkin() {
        BitmapFont font = generateFont();
        Skin skin = new Skin();
        skin.add("font", font);
        skin.add("title", font);
        skin.addRegions(new TextureAtlas(Gdx.files.internal(ATLAS_PATH)));
        skin.load(Gdx.files.internal(TEXTURE_JSON_PATH));
        return skin;
    }

    @Override
    public void create() {
        Skin skin = createSkin();
        Window playScreen = null;
        Window titleScreen = new TitleWindow(MENU_BACKGROUND_IMAGE_PATH, skin);
        try {
            playScreen = new PlayWindow(MENU_BACKGROUND_IMAGE_PATH, skin);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Gdx.app.exit();
            System.exit(1);
        }

        titleScreen.setWindowListener(wdm);

        wdm.addWindow(titleScreen, DukeManiaWindowState.TITLE);
        wdm.addWindow(playScreen, DukeManiaWindowState.PLAY);
        playScreen.setWindowListener(wdm);
        wdm.switchWindow(DukeManiaWindowState.TITLE);
    }

    /*
    @Override
    public void create() {

        Window playScreen = new PlayScreen();
        Window titleScreen = new TitleScreen();

        playScreen.setWindowListener(wdm);
        titleScreen.setWindowListener(wdm);

        wdm.addWindow(playScreen, DukeManiaWindowState.PLAY);
        wdm.addWindow(titleScreen, DukeManiaWindowState.TITLE);
        wdm.switchWindow(DukeManiaWindowState.TITLE);
    }
     */

    @Override
    public void render() {
        wdm.render();
    }

    @Override
    public void resize(final int width, final int height) {
        wdm.resize(width, height);
    }

    @Override
    public void dispose() {
        wdm.dispose();
    }

}
