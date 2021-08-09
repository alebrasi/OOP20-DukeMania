package it.dukemania.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import it.dukemania.DukeMania;
import it.dukemania.View.notesGraphics.Size;
import it.dukemania.View.notesGraphics.SizeImpl;

public class DesktopLauncher {
    public static void main(final String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        Size dimensions = new SizeImpl();
        config.setWindowedMode(dimensions.getSize().getX(), dimensions.getSize().getY());
        new Lwjgl3Application(new DukeMania(), config);
    }
}
