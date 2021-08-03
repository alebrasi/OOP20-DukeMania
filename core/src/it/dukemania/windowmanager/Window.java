package it.dukemania.windowmanager;

public interface Window {
    void create();
    void render();
    void dispose();
    void resize(int width, int height);
    void setWindowListener(SwitchWindowNotifier listener);
}
