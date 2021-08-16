package it.dukemania.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import it.dukemania.PlayScreen;
import it.dukemania.View.notesGraphics.Size;
import it.dukemania.View.notesGraphics.SizeImpl;

public class DesktopLauncher {
    public static void main(final String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        Size dimensions = new SizeImpl();
        config.setWindowedMode(dimensions.getSize().getX(), dimensions.getSize().getY());
        config.setResizable(false);
        new Lwjgl3Application(new PlayScreen(), config);
    }
}
