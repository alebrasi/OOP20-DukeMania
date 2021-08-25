package it.dukemania.View.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
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
    public final void create() {
        super.create();

        controller = new OptionWindowControllerImpl(data);

        float screenWidth = mainStage.getWidth();
        float screenHeight = mainStage.getHeight();
        Label lblPlayerName = new Label("Player name: ", skin);
        Label lblSampleRate = new Label("Sample rate: ", skin);
        Label lblBufferSize = new Label("Audio buffer size", skin);
        TextField txtPlayerName = new TextField(controller.getPlayerName(), skin);
        TextButton btnSave = new TextButton("Save name", skin);
        TextButton btnBack = new TextButton("Back to title screen", skin);
        SelectBox<Integer> slctSampleRates = new SelectBox<>(skin);
        SelectBox<Integer> slctBufferSizes = new SelectBox<>(skin);

        slctSampleRates.setItems(controller.getAvailableSampleRates());
        slctBufferSizes.setItems(controller.getBufferSizes());
        slctSampleRates.setSelected(controller.getCurrentSampleRate());
        slctBufferSizes.setSelected(controller.getCurrentBufferSize());

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
                controller.setBufferSize(slctBufferSizes.getSelected());
                controller.setSampleRate(slctSampleRates.getSelected());
            }
        });

        Table table = new Table(skin);

        table.add(lblPlayerName);
        table.add(txtPlayerName);
        table.row();
        table.add(lblSampleRate);
        table.add(slctSampleRates);
        table.row();
        table.add(lblBufferSize);
        table.add(slctBufferSizes);
        table.row();
        table.add(btnBack).colspan(2).expand().left();
        table.add(btnSave).colspan(2).expand().right();

        Container<Table> mainMenuContainer = new Container<>();
        mainMenuContainer.setActor(table);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - OPTION_WINDOW_OFFSET);
        mainStage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(mainStage);
    }
}