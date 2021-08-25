package it.dukemania.windowmanager;

import it.dukemania.model.GameModel;

import java.util.HashMap;

public class WindowManager implements SwitchWindowNotifier {
    private final HashMap<Integer, Window> windows = new HashMap<>();
    private Window currentWindow;

    public final void render() {
        currentWindow.render();
    }

    public final void resize(final int width, final int height) {
        currentWindow.resize(width, height);
    }

    public final void addWindow(final Window window, final WindowState associatedState) {
        window.setWindowListener(this);
        windows.put(associatedState.getStateValue(), window);
    }

    public final void switchWindow(final WindowState state, final GameModel data) {
        Window prevWindow = currentWindow;
        currentWindow = windows.get(state.getStateValue());
        currentWindow.receiveData(data);
        currentWindow.create();

        if (prevWindow != null) {
            prevWindow.dispose();
        }

    }

    public final void dispose() {
        currentWindow.dispose();
    }
}
