package it.dukemania.windowmanager;

import java.util.HashMap;

public class WindowManager implements SwitchWindowListener {
    private final HashMap<Integer, Window> windows = new HashMap<>();
    private Window currentWindow;

    public void render() {
        currentWindow.render();
    }

    public void resize(final int width, final int height) {
        currentWindow.resize(width, height);
    }

    public void addWindow(final Window window, final WindowState associatedState) {
        windows.put(associatedState.getStateValue(), window);
    }

    public void switchWindow(final WindowState state) {
        if (currentWindow != null) {
            currentWindow.dispose();
        }
        System.out.println("Switchy");
        currentWindow = windows.get(state.getStateValue());
        currentWindow.create();
    }

    public void dispose() {
        currentWindow.dispose();
    }
}
