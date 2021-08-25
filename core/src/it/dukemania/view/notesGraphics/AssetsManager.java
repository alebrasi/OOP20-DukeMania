package it.dukemania.view.notesGraphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;




public final class AssetsManager {
    private static AssetsManager instance;
    private boolean areLoaded;
    private final Map<String, Texture> textureAssociations = new HashMap<>();
    private final Map<String, Skin> skinAssociations = new HashMap<>();
    private final Map<String, FreeTypeFontGenerator> fontAssociations = new HashMap<>();
    private final Map<String, TextureAtlas> textureAtlasAssociations = new HashMap<>();
    private final Map<String, FileHandle> jsonAssociations = new HashMap<>();



    private static final int FONT_SIZE_MENU = 50;
    private static float fontBorderWidth = 0.5f;
    private static final Color FONT_COLOR = Color.BLACK;
    private static FreeTypeFontGenerator generator;
    private static final int FONT_SIZE = 40;


    private AssetsManager() {
        loadAll();
    }

    /***
     * 
     * @return a AssetManager. An instance is created if it does not already exist
     */
    public static AssetsManager getInstance() {
        if (instance == null) {
            instance = new AssetsManager();
        }
        return instance;
    }


    /***
     *
     * @param textureStr
     * @return a specific texture
     * 
     */
    public Texture getTexture(final String textureStr) {
        if (textureAssociations.get(textureStr) == null) { 
            throw new IllegalArgumentException(); 
        }
        return textureAssociations.get(textureStr);
    }

    /***
     * load all the textures.
     */
    private void loadTexture() {
        textureAssociations.put("pinkAndBlueButtons.png", new Texture(Gdx.files.internal("atlas/pinkAndBlueButtons.png")));
        textureAssociations.put("background.png", new Texture(Gdx.files.internal("textures/background.png")));
        textureAssociations.put("blueBackground.png", new Texture(Gdx.files.internal("textures/blueBackground.png")));
        textureAssociations.put("blueSpark.png", new Texture(Gdx.files.internal("textures/blueSpark.png")));
        textureAssociations.put("DukeMania.png", new Texture(Gdx.files.internal("textures/DukeMania.png")));
        textureAssociations.put("note.png", new Texture(Gdx.files.internal("textures/note.png")));
        textureAssociations.put("scoreboard.png", new Texture(Gdx.files.internal("textures/scoreboard.png"), true));
    }

    /***
     *
     * @param skinStr
     * @return a specific skin
     */
    public Skin getSkin(final String skinStr) {
        if (skinAssociations.get(skinStr) == null) { 
            throw new IllegalArgumentException(); 
        }
        return skinAssociations.get(skinStr);
    }

    /**
     * load all the skins.
     */
    private void loadSkin() {
        skinAssociations.put("skin_menu", generateSkinMenu());
    }


    /***
     * 
     * @param fontStr
     * @return a specific BitmapFont
     */
    public FreeTypeFontGenerator getBitmapFont(final String fontStr) {
        if (fontAssociations.get(fontStr) == null) { 
            throw new IllegalArgumentException(); 
        }
        return fontAssociations.get(fontStr);
    }

    /**
     * load all the BitmapFonts.
     */
    public void loadBitmapFont() {
        fontAssociations.put("scoreboard_font.TTF", new FreeTypeFontGenerator(Gdx.files.internal("fonts/scoreboard_font.TTF")));
        fontAssociations.put("agency-fb.ttf", new FreeTypeFontGenerator(Gdx.files.internal("fonts/agency-fb.ttf")));
    }

    /***
     * 
     * @param textureAtlasStr
     * @return a specific textureAtlas
     */
    public TextureAtlas getTextureAtlas(final String textureAtlasStr) {
        if (textureAtlasAssociations.get(textureAtlasStr) == null) {
            throw new IllegalArgumentException(); 
        }
        return textureAtlasAssociations.get(textureAtlasStr);
    }

    /**
     * load all the TextureAtlas.
     */
    public void loadTextureAtlas() {
        textureAtlasAssociations.put("pinkAndBlueButtons.atlas",
                new TextureAtlas(Gdx.files.internal("atlas/pinkAndBlueButtons.atlas")));
        textureAtlasAssociations.put("quantum-horizon-ui.atlas",
                new TextureAtlas(Gdx.files.internal("atlas/quantum-horizon-ui.atlas")));
    }


    /***
     * @throws
     * @param jsonStr
     * @return a specific json
     */
    public FileHandle getJson(final String jsonStr) {
        if (jsonAssociations.get(jsonStr) == null) { 
            throw new IllegalArgumentException(); 
        }
        return jsonAssociations.get(jsonStr);
    }

    /**
     * load all the json files.
     */
    public void loadJson() {
        jsonAssociations.put("quantum-horizon-ui.json", Gdx.files.internal("json/quantum-horizon-ui.json"));
    }

    private BitmapFont generateFontMenu() {
        generator = getBitmapFont("agency-fb.ttf");
        final FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = FONT_SIZE_MENU;
        parameter.borderWidth = fontBorderWidth;
        parameter.color = FONT_COLOR;
        final BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        return font;
    }

    public BitmapFont generateFontScoreboard() {
        generator = getBitmapFont("scoreboard_font.TTF");
        final FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = AssetsManager.FONT_SIZE;
        parameter.color = Color.WHITE;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 2;
        final BitmapFont fontScoreboard = generator.generateFont(parameter);
        fontScoreboard.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return fontScoreboard;
    }

    private Skin generateSkinMenu() {
        final Skin s1 = new Skin();
        final BitmapFont font = generateFontMenu();
        s1.add("font", font);
        s1.add("title", font);
        s1.addRegions(getTextureAtlas("quantum-horizon-ui.atlas"));
        s1.load(getJson("quantum-horizon-ui.json"));
        return s1;
    }

    /**
     * load all the resources.
     */
    public void loadAll() {
        if (!areLoaded) {
            loadTexture();
            loadTextureAtlas();
            loadBitmapFont();
            loadJson();
            loadSkin();
            areLoaded = true;
        }
    }

    /**
     * release all the resources.
     */
    public void dispose() {
        if (areLoaded) {
            textureAssociations.forEach((s, t) -> t.dispose());
            fontAssociations.forEach((s, t) -> t.dispose());
            textureAtlasAssociations.forEach((s, t) -> t.dispose());
            areLoaded = false;
        }
    }

}

