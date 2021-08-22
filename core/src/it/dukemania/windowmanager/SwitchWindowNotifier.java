package it.dukemania.windowmanager;

import it.dukemania.Model.GameModel;

public interface SwitchWindowNotifier {
    void switchWindow(WindowState state, GameModel data);
}
