package it.dukemania.windowmanager;

import it.dukemania.Model.GameModel;

import java.util.HashMap;

public class WindowManager implements SwitchWindowNotifier {
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

    public void switchWindow(final WindowState state, final GameModel data) {
        //TODO Do dispose?
        Window prevWindow = currentWindow;
        currentWindow = windows.get(state.getStateValue());
        currentWindow.receiveData(data);
        currentWindow.create();
        /*
        if (prevWindow != null) {
            prevWindow.dispose();
        }
         */
    }

    public void dispose() {
        //windows.forEach((i, w) -> w.dispose());
    }
}
