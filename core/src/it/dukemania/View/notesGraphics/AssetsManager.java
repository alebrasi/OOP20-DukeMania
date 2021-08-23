package it.dukemania.View.notesGraphics;

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
    private Map<String, Texture> textureAssociations = new HashMap<>();
    private Map<String, Skin> skinAssociations = new HashMap<>();
    private Map<String, FreeTypeFontGenerator> fontAssociations = new HashMap<>();
    private Map<String, TextureAtlas> textureAtlasAssociations = new HashMap<>();
    private Map<String, FileHandle> jsonAssociations = new HashMap<>();



    //private final String ATLAS_PATH = "textures/quantum-horizon-ui.atlas";
    private final String TEXTURE_JSON_PATH = "json/quantum-horizon-ui.json";
    private final String MENU_BACKGROUND_IMAGE_PATH = "DukeMania.png";
    private final String FONT_PATH = "fonts/agency-fb.ttf";
    private final static int fontSize = 50;
    private static float fontBorderWidth = 0.5f;
    private final static Color fontColor = Color.BLACK;
    private static FreeTypeFontGenerator generator;
    private static final int FONT_SIZE = 40;


    private AssetsManager() {
        load();
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
     * @throws FileNotFoundException 
     */
    public Texture getTexture(final String textureStr) throws NullPointerException {
        /*
        if (textureAssociations.isEmpty()) {
            load();
        }

         */
        if (textureAssociations.get(textureStr) == null) { 
            throw new NullPointerException(); 
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
    public Skin getSkin(final String skinStr) throws NullPointerException {
        /*
        if (skinAssociations.isEmpty()) {
            load();
        }

         */
        if (skinAssociations.get(skinStr) == null) { 
            throw new NullPointerException(); 
        }
        return skinAssociations.get(skinStr);
    }

    /***load all the skins.
     * */
    private void loadSkin() {
        skinAssociations.put("skin_menu", generateSkinMenu());
    }


    /***
     * 
     * @param fontStr
     * @return a specific BitmapFont
     */
    public FreeTypeFontGenerator getBitmapFont(final String fontStr) {
        /*
        if (fontAssociations.isEmpty()) {
            load();
        }

         */
        if (fontAssociations.get(fontStr) == null) { 
            throw new NullPointerException(); 
        }
        return fontAssociations.get(fontStr);
    }

    /***load all the BitmapFonts.
     * */
    public void loadBitmapFont() {
        fontAssociations.put("scoreboard_font.TTF", new FreeTypeFontGenerator(Gdx.files.internal("fonts/scoreboard_font.TTF")));
        fontAssociations.put("agency-fb.ttf", new FreeTypeFontGenerator(Gdx.files.internal("fonts/agency-fb.ttf")));
    }

    /***
     * 
     * @param textureStr
     * @return a specific textureAtlas
     */
    public TextureAtlas getTextureAtlas(final String textureAtlasStr) {
        /*
        if (textureAtlasAssociations.isEmpty()) {
            load();
        }

         */
        if (textureAtlasAssociations.get(textureAtlasStr) == null) {
            throw new NullPointerException(); 
        }
        return textureAtlasAssociations.get(textureAtlasStr);
    }

    /***load all the TextureAtlas.
     * */
    public void loadTextureAtlas() {
        textureAtlasAssociations.put("pinkAndBlueButtons.atlas", new TextureAtlas(Gdx.files.internal("atlas/pinkAndBlueButtons.atlas")));
        textureAtlasAssociations.put("quantum-horizon-ui.atlas", new TextureAtlas(Gdx.files.internal("atlas/quantum-horizon-ui.atlas")));
    }
    
    
    /***
     * 
     * @param jsonStr
     * @return a specific json
     */
    public FileHandle getJson(final String jsonStr) {
        /*
        if (textureAtlasAssociations.isEmpty()) {
            load();
        }

         */
        if (jsonAssociations.get(jsonStr) == null) { 
            throw new NullPointerException(); 
        }
        return jsonAssociations.get(jsonStr);
    }

    /***load all the json files.
     * */
    public void loadJson() {
        jsonAssociations.put("quantum-horizon-ui.json", Gdx.files.internal("json/quantum-horizon-ui.json"));
        //jsonAssociations.put("synthesizers_config.json", new FileHandl("json/synthesizers_config.json"));
    }

    private BitmapFont generateFontMenu() {
        generator = getBitmapFont("agency-fb.ttf");
        final FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.borderWidth = fontBorderWidth;
        parameter.color = fontColor;
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

    /***load all the resources.
     * */
    public void load() {
        if (!areLoaded) {
            loadTexture();
            loadTextureAtlas();
            loadBitmapFont();
            loadJson();
            loadSkin();
            areLoaded = true;
        }
    }

    /***release all the resources.
     * 
     */
    public void dispose() {
        if (areLoaded) {
            skinAssociations.forEach((s, t) -> t.dispose());
            textureAssociations.forEach((s, t) -> t.dispose());
            fontAssociations.forEach((s, t) -> t.dispose());
            textureAtlasAssociations.forEach((s, t) -> t.dispose());
            areLoaded = false;
        }
    }

}

