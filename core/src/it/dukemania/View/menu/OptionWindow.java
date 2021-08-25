package it.dukemania.View.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import it.dukemania.Controller.option.OptionWindowController;
import it.dukemania.Controller.option.OptionWindowControllerImpl;
import it.dukemania.windowmanager.DukeManiaWindowState;


public class OptionWindow extends AbstractView {

    private OptionWindowController controller;
    private static final int OPTION_WINDOW_OFFSET = 200;

    public OptionWindow(final String backgroundPath, final Skin skin) {
        super(backgroundPath, skin);
    }

    @Override
    public void create() {
        super.create();

        controller = new OptionWindowControllerImpl(data);

        float screenWidth = mainStage.getWidth();
        float screenHeight = mainStage.getHeight();
        Label lblPlayerName = new Label("Player name: ", skin);
        TextField txtPlayerName = new TextField(controller.getPlayerName(), skin);
        TextButton btnSave = new TextButton("Save name", skin);
        TextButton btnBack = new TextButton("Back to title screen", skin);

        btnBack.addListener(new ClickListener() {
           @Override
           public void clicked(final InputEvent event, final float x, final float y) {
               switchWindowNotifier.switchWindow(DukeManiaWindowState.TITLE, data);
           }
        });

        btnSave.addListener(new ClickListener() {
           @Override
           public void clicked(final InputEvent event, final float x, final float y) {
               controller.setPlayerName(txtPlayerName.getText());
           }
        });

        Table table = new Table(skin);

        //TODO Remove magic numbers
        table.add(lblPlayerName);
        table.add(txtPlayerName);
        table.row().padBottom(20);
        table.add(btnBack).colspan(2).expand().left();
        table.add(btnSave).colspan(2).expand().right();

        Container<Table> mainMenuContainer = new Container<>();
        mainMenuContainer.setActor(table);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - OPTION_WINDOW_OFFSET);
        mainStage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(mainStage);
    }
}
