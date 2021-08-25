package it.dukemania.midi;

public final class FactoryConfigurator {

    private FactoryConfigurator() { }
    /**
     * this method return a FactoryImpl or PercussionFactoryImpl based on the channel.
     * @param channel the MIDI channel number
     * @return a factory
     */
    public static AbstractFactory getFactory(final int channel) {
        return channel == 10 ? PercussionFactoryImpl.getInstance() : FactoryImpl.getInstance();
     }


}

