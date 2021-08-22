package it.dukemania.View.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.dukemania.Model.serializers.ConfigurationsModel;
import it.dukemania.Model.serializers.ConfigurationsModelImpl;
import it.dukemania.Model.serializers.leaderboard.SongLeaderBoard;
import it.dukemania.View.AbstractView;
import it.dukemania.midi.Song;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactoryImpl;
import it.dukemania.windowmanager.DukeManiaWindowState;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OptionWindow extends AbstractView {
    public OptionWindow(final String backgroundPath, final Skin skin) {
        super(backgroundPath, skin);
    }

    @Override
    public void create() {
        super.create();
        float screenWidth = mainStage.getWidth();
        float screenHeight = mainStage.getHeight();
        Label lblPlayerName = new Label("Player name: ", skin);
        TextField txtPlayerName = new TextField("P1", skin);
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
               data.setPlayerName(txtPlayerName.getText());
               /*
               Map<String, Integer> scores = Map.of("Pinco", -420, "Pallino", -69, "Topolino", 69420);
               List<SongLeaderBoard> l = List.of(new SongLeaderBoard("dffsfdsf", scores), new SongLeaderBoard("kjshfdjkhsjkfhsf", scores));
                */
           }
        });

        Table table = new Table(skin);

        table.add(lblPlayerName);
        table.add(txtPlayerName);
        table.row().padBottom(20);
        table.add(btnBack).colspan(2).expand().left();
        table.add(btnSave).colspan(2).expand().right();

        Container<Table> mainMenuContainer = new Container<>();
        mainMenuContainer.setActor(table);
        mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - 200);
        mainStage.addActor(mainMenuContainer);
        Gdx.input.setInputProcessor(mainStage);
    }
}
