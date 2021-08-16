package it.dukemania.windowmanager;

public enum DukeManiaWindowState implements WindowState {
    PLAY(3),
    TITLE(0),
    OPTIONS(1),
    SONG_SELECTION(2);

    private final int stateValue;

    DukeManiaWindowState(final int stateValue) {
        this.stateValue = stateValue;
    }

    @Override
    public int getStateValue() {
        return this.stateValue;
    }
}
