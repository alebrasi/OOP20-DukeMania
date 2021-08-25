package it.dukemania;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import it.dukemania.model.GameModel;
import it.dukemania.view.PlayScreen;
import it.dukemania.view.menu.LeaderboardWindow;
import it.dukemania.view.menu.OptionWindow;
import it.dukemania.view.menu.SongSelectionWindow;
import it.dukemania.view.menu.TitleWindow;
import it.dukemania.view.notesGraphics.AssetsManager;
import it.dukemania.windowmanager.DukeManiaWindowState;
import it.dukemania.windowmanager.Window;
import it.dukemania.windowmanager.WindowManager;

import java.security.NoSuchAlgorithmException;

public class DukeMania extends ApplicationAdapter {

    private final WindowManager wdm = new WindowManager();
    private static final String MENU_BACKGROUND_IMAGE_PATH = "DukeMania.png";
    private final GameModel data = new GameModel();

    @Override
    public final void create() {
        final Skin skin = AssetsManager.getInstance().getSkin("skin_menu");
        Window songSelectionScreen = null;
        final Window titleScreen = new TitleWindow(MENU_BACKGROUND_IMAGE_PATH, skin);
        final Window optionScreen = new OptionWindow(MENU_BACKGROUND_IMAGE_PATH, skin);
        final Window leaderBoardScreen = new LeaderboardWindow(MENU_BACKGROUND_IMAGE_PATH, skin);
        final Window playScreen = new PlayScreen();
        try {
            songSelectionScreen = new SongSelectionWindow(MENU_BACKGROUND_IMAGE_PATH, skin);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Gdx.app.exit();
            System.exit(1);
        }
        //Add windows
        wdm.addWindow(titleScreen, DukeManiaWindowState.TITLE);
        wdm.addWindow(optionScreen, DukeManiaWindowState.OPTIONS);
        wdm.addWindow(songSelectionScreen, DukeManiaWindowState.SONG_SELECTION);
        wdm.addWindow(leaderBoardScreen, DukeManiaWindowState.LEADERBOARD);
        wdm.addWindow(playScreen, DukeManiaWindowState.PLAY);
        //Switch to the title window
        wdm.switchWindow(DukeManiaWindowState.TITLE, data);
    }

    @Override
    public final void render() {
        wdm.render();
    }

    @Override
    public final void resize(final int width, final int height) {
        wdm.resize(width, height);
    }

    @Override
    public final void dispose() {
        wdm.dispose();
        AssetsManager.getInstance().dispose();
        System.exit(0);
    }

}
