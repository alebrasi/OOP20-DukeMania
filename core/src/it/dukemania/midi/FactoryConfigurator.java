package it.dukemania.midi;

public class FactoryConfigurator {
    private static final PercussionFactoryImpl PERCUSSION_FACTORY = new PercussionFactoryImpl();
    private static final FactoryImpl FACTORY = new FactoryImpl();

    /**
     * this method return a Factory or PercussionFactory based on the channel.
     * @param channel
     * @return a factory
     */
    public static AbstractFactory getFactory(final int channel) {
        return channel == 10 ? PERCUSSION_FACTORY : FACTORY;
     }
}

