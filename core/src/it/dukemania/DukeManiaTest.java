package it.dukemania;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import it.dukemania.Model.GameModel;
import it.dukemania.View.menu.LeaderboardWindow;
import it.dukemania.View.menu.OptionWindow;
import it.dukemania.View.menu.SongSelectionWindow;
import it.dukemania.View.menu.TitleWindow;
import it.dukemania.View.notesGraphics.AssetsManager;
import it.dukemania.windowmanager.DukeManiaWindowState;
import it.dukemania.windowmanager.Window;
import it.dukemania.windowmanager.WindowManager;

import java.security.NoSuchAlgorithmException;

public class DukeManiaTest extends ApplicationAdapter {

    private final WindowManager wdm = new WindowManager();
    private final String MENU_BACKGROUND_IMAGE_PATH = "DukeMania.png";
    private GameModel data = new GameModel();

    @Override
    public void create() {
        Skin skin = AssetsManager.getInstance().getSkin("skin_menu");
        Window songSelectionScreen = null;
        Window titleScreen = new TitleWindow(MENU_BACKGROUND_IMAGE_PATH, skin);
        Window optionScreen = new OptionWindow(MENU_BACKGROUND_IMAGE_PATH, skin);
        Window leaderBoardScreen = new LeaderboardWindow(MENU_BACKGROUND_IMAGE_PATH, skin);
        Window playScreen = new PlayScreen();
        try {
            songSelectionScreen = new SongSelectionWindow(MENU_BACKGROUND_IMAGE_PATH, skin);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Gdx.app.exit();
            System.exit(1);
        }


        wdm.addWindow(titleScreen, DukeManiaWindowState.TITLE);
        wdm.addWindow(optionScreen, DukeManiaWindowState.OPTIONS);
        wdm.addWindow(songSelectionScreen, DukeManiaWindowState.SONG_SELECTION);
        wdm.addWindow(leaderBoardScreen, DukeManiaWindowState.LEADERBOARD);
        wdm.addWindow(playScreen, DukeManiaWindowState.PLAY);
        wdm.switchWindow(DukeManiaWindowState.TITLE, data);
        titleScreen.setWindowListener(wdm);
        optionScreen.setWindowListener(wdm);
        songSelectionScreen.setWindowListener(wdm);
        playScreen.setWindowListener(wdm);
        leaderBoardScreen.setWindowListener(wdm);
    }

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
        AssetsManager.getInstance().dispose();
    }

}
