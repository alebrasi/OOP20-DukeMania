package it.dukemania.View.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import it.dukemania.windowmanager.DukeManiaWindowState;

public class TitleWindow extends AbstractView {

    public TitleWindow(String backgroundPath, Skin skin) {
        super(backgroundPath, skin);
    }

    @Override
    public void create() {
        super.create();

        float screenWidth = mainStage.getWidth();
        float screenHeight = mainStage.getHeight();

        TextButton btnPlay = new TextButton("PLAY", skin);
        TextButton btnQuit = new TextButton("QUIT", skin);
        TextButton btnOptions = new TextButton("OPTIONS", skin);

        btnOptions.pack();
        btnPlay.pack();
        btnQuit.pack();

        btnPlay.setTransform(true);
        btnQuit.setTransform(true);
        btnOptions.setTransform(true);

        btnPlay.addListener(new ClickListener() {

            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                if (switchWindowNotifier != null) {
                    switchWindowNotifier.switchWindow(DukeManiaWindowState.SONG_SELECTION, data);
                }
            }
        });

        btnOptions.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                if (switchWindowNotifier != null) {
                    switchWindowNotifier.switchWindow(DukeManiaWindowState.OPTIONS, data);
                }
            }
        });

        btnQuit.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                Gdx.app.exit();
                dispose();
                System.exit(0);
            }
        });

        Container<Table> mainMenuContainer = new Container<>();
        Table table = new Table(skin);

        table.setDebug(false);
        table.add(btnPlay);
        table.row();
        table.add(btnOptions);
        table.row();
        table.add(btnQuit);

        table.getCells().forEach(s -> s.padTop(20f));
        mainMenuContainer.setActor(table);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - 200);

        mainStage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(mainStage);
    }
}
