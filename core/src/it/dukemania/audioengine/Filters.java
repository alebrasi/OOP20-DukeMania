package it.dukemania.audioengine;

public final class Filters {

    private Filters() {

    }

    /**
     * Modify a buffer, take a sample every sampToHold samples, then make every sample of that interval equal to that sample.
     * @param buff the buffer that has to be modifies
     * @param sampToHold how many samples to make the same
     */
    public static void sampleNHold(final double [] buff, final long sampToHold) {
        double actualSample = 0;
        for (int i = 0; i < buff.length; i++) {
            if (i % sampToHold == 0) {
                actualSample = buff[i];
            } else {
                buff[i] = actualSample;
            }
        }
    }

    /**
     * Frequency modulate a wave.
     * @param carrier the carrier wave
     * @param modulator the modulator
     * @param carrierFreq the frequency of the carrier wave
     */
    public static void frequencyModulation(final double [] carrier, final double [] modulator, final float carrierFreq) {
        float period = Settings.SAMPLE_RATE / carrierFreq;
        if (carrier.length > modulator.length) {
            return;
        }
        for (float i = 0; i < carrier.length; i++) {
            carrier[(int) i] = (float) (1 * (Math.sin(2.0 * Math.PI * carrier[(int) i] * period + 1 * Math.sin(2 * Math.PI * modulator[(int) i] * period))));
        }
    }

}
