package it.dukemania.midi;

public class FactoryConfigurator {
    private static final PercussionFactoryImpl PERCUSSION_FACTORY = new PercussionFactoryImpl();
    private static final FactoryImpl FACTORY = new FactoryImpl();

    public static AbstractFactory getFactory(final int channel) {
        return channel == 10 ? PERCUSSION_FACTORY : FACTORY;
     }
}

