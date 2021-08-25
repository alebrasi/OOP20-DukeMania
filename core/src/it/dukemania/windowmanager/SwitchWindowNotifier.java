package it.dukemania.windowmanager;

import it.dukemania.model.GameModel;

public interface SwitchWindowNotifier {
    void switchWindow(WindowState state, GameModel data);
}
