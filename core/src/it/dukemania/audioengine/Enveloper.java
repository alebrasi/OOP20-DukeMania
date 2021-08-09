package it.dukemania.audioengine;

public class Enveloper {
    private final float atkVol;
    private final float step1;
    private final long atk;
    private final long rel;

    /**
     * Create an Enveloper template which can later be used to create envelopers,
     * since the sustain time of a note may vary.
     * @param atk ms that will take the volume to raise from 0 to atk_vol
     * @param atk_vol the maximum volume reachable by the enveloper
     * @param rel ms that will take the volume to drop from the release volume to 0
     */
    public Enveloper(long atk, float atk_vol, long rel) {
        this.atk = atk;
        this.rel = rel;
        this.atkVol = atk_vol;
        this.step1 = (atk_vol / atk) / Settings.SAMPLESPERMILLI;
    }

    /**
     * Get the total time that the enveloper passes during attack and release.
     * @return The total samples
     */
    public long getTime() {
        return (long) ((this.atk + this.rel) * Settings.SAMPLESPERMILLI);
    }

    /**
     * Create the actual volume enveloper, using the class parameters.
     * @return the volume enveloper as an Iterator
     */
    public BufferManager<Float> createBufferManager(final double [] buff) {
        return new BufferManager<Float>() {
            private float actual = 0f;
            private float totalSamples = 0l;
            private int processedSamples = 0;
            private float resetStep = 0;
            private float step2 = 0;
            private int reset = -1;
            /**
             * {@inheritDoc}
             */
            @Override
            public void refresh(long ttl) {
                totalSamples = (ttl * Settings.SAMPLESPERMILLI) + Settings.ATTENUATION;
                resetStep = actual / (Settings.ATTENUATION);
                reset = Settings.ATTENUATION;
                step2 = 0;
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return reset >= 0 || processedSamples < totalSamples || actual > 0;
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public Float next() {

                if (reset > 0) {
                    actual -= resetStep;
                } else {
                    if (reset == 0) {
                        this.processedSamples = 0;
                        this.actual = 0;
                    }
                    if (this.processedSamples >= this.totalSamples) {
                        if (step2 == 0) {
                            step2 = (actual / (rel * Settings.SAMPLESPERMILLI)) * -1;
                        }
                        if (actual <= 0) {
                            this.processedSamples++;
                            return 0f;
                        }
                        actual += step2;
                    } else {
                        if (actual >= atkVol) {
                            return atkVol * (float)buff[this.processedSamples++];
                        }
                        actual += step1;
                    }
                }

                this.reset--;
                return (float)buff[this.processedSamples++] * actual;
            }
        };
    }
}
