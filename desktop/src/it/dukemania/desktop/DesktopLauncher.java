package it.dukemania.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import it.dukemania.DukeManiaTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//new Lwjgl3Application(new DukeMania(), config);
		//new Lwjgl3Application(new PlayScreen(), config);
		new Lwjgl3Application(new DukeManiaTest(), config);
		//new Lwjgl3Application(new TitleScreen(), config);
	}
}
