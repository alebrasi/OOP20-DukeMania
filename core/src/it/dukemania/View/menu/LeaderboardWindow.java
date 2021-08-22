package it.dukemania.View.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import it.dukemania.Controller.leaderboard.LeaderboardController;
import it.dukemania.Controller.leaderboard.LeaderboardControllerImpl;
import it.dukemania.View.AbstractView;
import it.dukemania.windowmanager.DukeManiaWindowState;


public class LeaderboardWindow extends AbstractView {

    private static final float TABLE_BACKGROUND_OPACITY = 0.2f;
    private static final int PADDING = 40;
    private static final int LEADERBOARD_HEIGHT = 400;
    private LeaderboardController controller;
    private Texture tableBackgroundTexture;

    public LeaderboardWindow(final String backgroundPath, final Skin skin) {
        super(backgroundPath, skin);
    }

    @Override
    public void create() {
        super.create();
        Gdx.graphics.setResizable(true);
        controller = new LeaderboardControllerImpl(data);
        tableBackgroundTexture = new Texture(Gdx.files.internal("background.png"));
        float screenWidth = mainStage.getWidth();
        float screenHeight = mainStage.getHeight();
        Table table = new Table(skin);
        Table tblLeaderboard = new Table(skin);
        ScrollPane scrlTblLeaderBoard = new ScrollPane(table);
        TextButton btnQuit = new TextButton("QUIT", skin);
        TextButton btnPlayAgain = new TextButton("Play another song", skin);

        scrlTblLeaderBoard.setFadeScrollBars(false);
        scrlTblLeaderBoard.setScrollbarsVisible(true);

        controller.getLeaderboard().forEach(t -> {
            table.add(new Label(t.getX(), skin)).uniformX();
            table.add(new Label(t.getY(), skin)).uniformX();
            table.row();
        });

        NinePatch patch = new NinePatch(tableBackgroundTexture, 0, 0, 0, 0);
        NinePatchDrawable drawablePatch = new NinePatchDrawable(patch);
        patch.setColor(new Color(1, 1, 1, TABLE_BACKGROUND_OPACITY));

        tblLeaderboard.add(scrlTblLeaderBoard).height(LEADERBOARD_HEIGHT).colspan(2).expand().fillX()
                                                                        .padBottom(PADDING).padTop(PADDING);
        tblLeaderboard.row();
        tblLeaderboard.add(btnQuit).padRight(PADDING).padLeft(PADDING);
        tblLeaderboard.add(btnPlayAgain).padRight(PADDING).padLeft(PADDING);
        tblLeaderboard.setBackground(drawablePatch);

        btnPlayAgain.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                switchWindowNotifier.switchWindow(DukeManiaWindowState.SONG_SELECTION, data);
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
        mainMenuContainer.setActor(tblLeaderboard);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - 200);
        mainStage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(mainStage);
    }
}
