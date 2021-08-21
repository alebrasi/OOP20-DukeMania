package it.dukemania.View.notesGraphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;



public final class AssetManager {
    private static AssetManager instance;
    //private String value;
    private static boolean areLoaded;

    private AssetManager() {
        //this.value = value;
    }

    /***
     * 
     * @return a AssetManager. An instance is created if it does not already exist
     */
    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }


    public static class TextureFiles {

        private static Map<String, Texture> textureAssociations = new HashMap<>();

        /***
         * 
         * @param textureStr
         * @return a specific texture
         */
        public static Texture get(final String textureStr) {
            if (textureAssociations.isEmpty()) {
                load();
            }
            return textureAssociations.get(textureStr);
        }

        /***
         * load all the textures.
         */
        public static void load() {
            textureAssociations.put("galaxy.png", new Texture(Gdx.files.internal("galaxy.png")));
        }
    }


    public static class SkinFiles {

        private static Map<String, Skin> skinAssociations = new HashMap<>();

        /***
         * 
         * @param skinStr
         * @return a specific skin
         */
        public static Skin get(final String skinStr) {
            if (skinAssociations.isEmpty()) {
                load();
            }
            return skinAssociations.get(skinStr);
        }

        /***load all the skins.
         * */
        public static void load() {
           //skinAssociations.put("Configure Song", skin);
        }
    }

    public static class FontFiles {

        private static Map<String, BitmapFont> fontAssociations = new HashMap<>();

        /***
         * 
         * @param fontStr
         * @return a specific BitmapFont
         */
        public static BitmapFont get(final String fontStr) {
            if (fontAssociations.isEmpty()) {
                load();
            }
            return fontAssociations.get(fontStr);
        }

        /***load all the BitmapFonts.
         * */
        public static void load() {
            //fontAssociations.put("fonts/agency-fb.ttf", );
        }
    }

    public static class TextureAtlasFiles {

        private static Map<String, TextureAtlas> textureAtlasAssociations = new HashMap<>();

        /***
         * 
         * @param textureStr
         * @return a specific textureAtlas
         */
        public static TextureAtlas get(final String textureStr) {
            if (textureAtlasAssociations.isEmpty()) {
                load();
            }
            return textureAtlasAssociations.get(textureStr);
        }

        /***load all the TextureAtlas.
         * */
        public static void load() {
            textureAtlasAssociations.put("pink and blue button.atlas", new TextureAtlas(Gdx.files.internal("pink and blue button.atlas")));
        }
    }

    /***load all the resources.
     * */
    public static void loadAll() {
        if (!areLoaded) {
            TextureFiles.load();
            SkinFiles.load();
            FontFiles.load();
            areLoaded = true;
        }
    }

}
