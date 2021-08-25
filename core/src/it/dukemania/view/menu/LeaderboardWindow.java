package it.dukemania.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import it.dukemania.controller.leaderboard.LeaderboardController;
import it.dukemania.controller.leaderboard.LeaderboardControllerImpl;
import it.dukemania.view.notesGraphics.AssetsManager;
import it.dukemania.windowmanager.DukeManiaWindowState;


public class LeaderboardWindow extends AbstractView {

    private static final float TABLE_BACKGROUND_OPACITY = 0.2f;
    private static final int PADDING = 40;
    private static final int LEADERBOARD_HEIGHT = 400;
    private LeaderboardController controller;
    private Texture tableBackgroundTexture;
    private static int LEADERBOARD_PADDING = 200;

    public LeaderboardWindow(final String backgroundPath, final Skin skin) {
        super(backgroundPath, skin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create() {
        super.create();
        Gdx.graphics.setResizable(true);
        controller = new LeaderboardControllerImpl(data);
        tableBackgroundTexture = AssetsManager.getInstance().getTexture("background.png");
        float screenWidth = mainStage.getWidth();
        float screenHeight = mainStage.getHeight();
        Table table = new Table(skin);
        Table tblLeaderboard = new Table(skin);
        ScrollPane scrlTblLeaderBoard = new ScrollPane(table);
        Label lblLeaderBoard = new Label("LEADERBOARD", skin);
        TextButton btnQuit = new TextButton("QUIT", skin);
        TextButton btnPlayAgain = new TextButton("Play another song", skin);
        TextButton btnPlay = new TextButton("Play again", skin);

        scrlTblLeaderBoard.setFadeScrollBars(false);
        scrlTblLeaderBoard.setScrollbarsVisible(true);

        controller.getLeaderboard().forEach(t -> {
            table.add(new Label(t.getX() + "------->", skin)).uniformX();
            table.add(new Label(t.getY(), skin)).uniformX();
            table.row();
        });

        NinePatch patch = new NinePatch(tableBackgroundTexture, 0, 0, 0, 0);
        NinePatchDrawable drawablePatch = new NinePatchDrawable(patch);
        patch.setColor(new Color(1, 1, 1, TABLE_BACKGROUND_OPACITY));

        tblLeaderboard.add(lblLeaderBoard).colspan(3).expand().center();
        tblLeaderboard.row();
        tblLeaderboard.add(scrlTblLeaderBoard).height(LEADERBOARD_HEIGHT).colspan(3).expand().fillX()
                                                                        .padBottom(PADDING).padTop(PADDING);
        tblLeaderboard.row();
        tblLeaderboard.add(btnQuit).padRight(PADDING).padLeft(PADDING);
        tblLeaderboard.add(btnPlay);
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

        btnPlay.addListener(new ClickListener() {
           @Override
            public void clicked(final InputEvent event, final float x, final float y) {
               switchWindowNotifier.switchWindow(DukeManiaWindowState.PLAY, data);
           }
        });

        Container<Table> mainMenuContainer = new Container<>();
        mainMenuContainer.setActor(tblLeaderboard);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - LEADERBOARD_PADDING);
        mainStage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(mainStage);
    }
}
