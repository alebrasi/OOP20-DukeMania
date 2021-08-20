package it.dukemania.View.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import it.dukemania.Controller.leaderboard.LeaderboardController;
import it.dukemania.Controller.leaderboard.LeaderboardControllerImpl;
import it.dukemania.View.AbstractView;
import it.dukemania.windowmanager.DukeManiaWindowState;

import java.util.Locale;

public class LeaderboardWindow extends AbstractView {

    private final LeaderboardController controller = new LeaderboardControllerImpl();
    private String songHash;
    private String playerName;

    public LeaderboardWindow(final String backgroundPath, final Skin skin) {
        super(backgroundPath, skin);
    }

    @Override
    public void create() {
        super.create();
        //"dffsfdsf", "Duke", 69420
        Object[] d = (Object[]) data;
        float screenWidth = mainStage.getWidth();
        float screenHeight = mainStage.getHeight();
        Table table = new Table(skin);
        Table tblLeaderboard = new Table(skin);
        TextButton btnQuit = new TextButton("QUIT", skin);
        TextButton btnPlayAgain = new TextButton("Play another song", skin);

        controller.getLeaderboard((String) d[1], (Integer) d[2], (String) d[0]).forEach(t -> {
            table.add(new Label(t.getX(), skin)).uniformX();
            table.add(new Label(t.getY(), skin)).uniformX();
            table.row();
        });

        table.add(btnQuit);
        table.add(btnPlayAgain);

        btnPlayAgain.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                switchWindowNotifier.switchWindow(DukeManiaWindowState.SONG_SELECTION, null);
            }
        });

        btnQuit.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                Gdx.app.exit();
                System.exit(0);
            }
        });

        Container<Table> mainMenuContainer = new Container<>();
        mainMenuContainer.setActor(table);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - 200);
        mainStage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(mainStage);
    }
}
