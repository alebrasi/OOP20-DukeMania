package it.dukemania.windowmanager;

public interface Window {
    void create();
    void render();
    void dispose();
    void resize(int width, int height);
    void receiveData(Object data);
    void setWindowListener(SwitchWindowNotifier listener);
}
