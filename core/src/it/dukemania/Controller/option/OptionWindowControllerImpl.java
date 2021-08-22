package it.dukemania.Controller.option;

import it.dukemania.Model.GameModel;

public class OptionWindowControllerImpl implements OptionWindowController {

    private final GameModel data;

    public OptionWindowControllerImpl(final GameModel data) {
        this.data = data;
    }

    @Override
    public void setPlayerName(final String name) {
        data.setPlayerName(name);
    }

    @Override
    public String getPlayerName() {
        return data.getPlayerName();
    }
}
