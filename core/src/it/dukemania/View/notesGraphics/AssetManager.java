package it.dukemania.View.notesGraphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;



public class AssetManager {

    public static class TextureFiles {

        private static Map<String, Texture> textureAssociations = new HashMap<>();


        public static Texture get(final String textureStr) {
            if (textureAssociations.isEmpty()) {
                load();
            }
            return textureAssociations.get(textureStr);
        }

        public static void load() {
            textureAssociations.put("galaxy.png", new Texture(Gdx.files.internal("galaxy.png")));
        }
    }


    public static class SkinFiles {

        private static Map<String, Skin> skinAssociations = new HashMap<>();


        public static Skin get(final String skinStr) {
            if (skinAssociations.isEmpty()) {
                load();
            }
            return skinAssociations.get(skinStr);
        }

        public static void load() {
           //skinAssociations.put("Configure Song", skin);
        }
    }

    public static class FontFiles {

        private static Map<String, BitmapFont> fontAssociations = new HashMap<>();


        public static BitmapFont get(final String fontStr) {
            if (fontAssociations.isEmpty()) {
                load();
            }
            return fontAssociations.get(fontStr);
        }

        public static void load() {
            //fontAssociations.put("fonts/agency-fb.ttf", );
        }
    }

    public static class TextureAtlasFiles {

        private static Map<String, TextureAtlas> textureAtlasAssociations = new HashMap<>();


        public static TextureAtlas get(final String textureStr) {
            if (textureAtlasAssociations.isEmpty()) {
                load();
            }
            return textureAtlasAssociations.get(textureStr);
        }

        public static void load() {
            textureAtlasAssociations.put("pink and blue button.atlas", new TextureAtlas(Gdx.files.internal("pink and blue button.atlas")));
        }
    }
}
