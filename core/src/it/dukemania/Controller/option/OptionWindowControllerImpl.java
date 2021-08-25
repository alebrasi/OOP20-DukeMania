package it.dukemania.Controller.option;

import it.dukemania.Model.GameModel;
import it.dukemania.audioengine.Settings;

public class OptionWindowControllerImpl implements OptionWindowController {

    private final GameModel data;

    public OptionWindowControllerImpl(final GameModel data) {
        this.data = data;
    }

    @Override
    public final void setPlayerName(final String name) {
        data.setPlayerName(name);
    }

    @Override
    public final String getPlayerName() {
        return data.getPlayerName();
    }

    @Override
    public final Integer[] getAvailableSampleRates() {
        return Settings.AVAILABLE_SAMPLE_RATES;
    }

    @Override
    public final Integer[] getBufferSizes() {
        return Settings.AVAILABLE_BUFFER_SIZES;
    }

    @Override
    public final void setSampleRate(final int sampleRate) {
        Settings.SAMPLE_RATE = sampleRate;
    }

    @Override
    public final void setBufferSize(final int bufferSize) {
        Settings.BUFFER_LENGHT = bufferSize;
    }

    @Override
    public final int getCurrentSampleRate() {
        return (int) Settings.SAMPLE_RATE;
    }

    @Override
    public final int getCurrentBufferSize() {
        return Settings.BUFFER_LENGHT;
    }
}