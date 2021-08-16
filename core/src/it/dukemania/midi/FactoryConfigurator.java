package it.dukemania.midi;

public class FactoryConfigurator {
    private static final PercussionFactory PERCUSSION_FACTORY = new PercussionFactory();
    private static final Factory FACTORY = new Factory();

    public static AbstractFactory getFactory(final int channel) {
        return channel == 10 ? PERCUSSION_FACTORY : FACTORY;
     }
}

