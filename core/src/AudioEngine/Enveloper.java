package AudioEngine;

import java.util.Iterator;

public class Enveloper {
    private final float atkVol;
    private final float step1;
    private final float step2;

    /**
     * Create an Enveloper template which can later be used to create envelopers,
     * since the sustain time of a note may vary
     * @param atk ms that will take the volume to raise from 0 to atk_vol
     * @param atk_vol the maximum volume reachable by the enveloper
     * @param rel ms that will take the volume to drop from the release volume to 0
     */
    public Enveloper(long atk, float atk_vol, long rel){
        this.atkVol = atk_vol;
        this.step1 = (atk_vol / atk) / Settings.SAMPLESPERMILLI;
        this.step2 = (1f / rel) / (Settings.SAMPLESPERMILLI * -1);
    }

    /**
     * Create the actual volume enveloper, using the class parameters
     * @param ttl time to live (the note sustain in ms)
     * @return the volume enveloper as an Iterator
     */
    public Iterator<Float> createEnveloper(Long ttl){
        return new Iterator<Float>() {
            private float actual = 0.01f;
            private final float totalTime = (float) ttl * Settings.SAMPLESPERMILLI;
            private long processedSamples = 0L;
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return this.actual > 0;
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public Float next() {
                return this.processedSamples++ >= this.totalTime ?
                        this.actual <= 0 ? 0 : (this.actual += step2) :
                        this.actual >= atkVol ? atkVol : (this.actual += step1);
            }
        };
    }
}
