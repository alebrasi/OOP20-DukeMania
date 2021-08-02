package AudioEngine;

public class Enveloper {
    private final float atkVol;
    private final float step1;
    private final long atk;
    private final long rel;

    /**
     * Create an Enveloper template which can later be used to create envelopers,
     * since the sustain time of a note may vary
     * @param atk ms that will take the volume to raise from 0 to atk_vol
     * @param atk_vol the maximum volume reachable by the enveloper
     * @param rel ms that will take the volume to drop from the release volume to 0
     */
    public Enveloper(long atk, float atk_vol, long rel){
        this.atk = atk;
        this.rel = rel;
        this.atkVol = atk_vol;
        this.step1 = (atk_vol / atk) / Settings.SAMPLESPERMILLI;
    }

    /**
     * Get the total time that the enveloper passes during attack and release
     * @return The total samples
     */
    public long getTime(){
        return (long) ((this.atk + this.rel) * Settings.SAMPLESPERMILLI);
    }

    /**
     * Create the actual volume enveloper, using the class parameters
     * @return the volume enveloper as an Iterator
     */
    public EnveloperIterator<Float> createEnveloper(){
        return new EnveloperIterator<Float>() {
            private float actual = 0f;
            private float totalSamples = 0l;
            private long processedSamples = 0l;
            private float resetStep = 0;
            private float step2 = 0;
            private final int ATTENUATION = 300;
            /**
             * {@inheritDoc}
             */
            @Override
            public void refresh(long ttl) {
                totalSamples = (ttl * Settings.SAMPLESPERMILLI) + ATTENUATION;
                resetStep = actual / (ATTENUATION);
                processedSamples = ATTENUATION * -1;
                step2 = 0;
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return actual > 0 || processedSamples < totalSamples;
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public Float next() {

                if(this.processedSamples < 0){
                    actual -= resetStep;
                }else{
                    if(this.processedSamples >= this.totalSamples){
                        if (step2 == 0) {
                            step2 = (actual / (rel * Settings.SAMPLESPERMILLI)) * -1;
                        }
                        if(actual <= 0){
                            this.processedSamples++; // TODO procsamples++ poterbbne non servire
                            return 0f;
                        }else{
                            actual += step2;
                        }
                    }else{
                        if(actual >= atkVol){
                            this.processedSamples++;
                            return atkVol;
                        }else{
                            actual += step1;
                        }
                    }
                }
                this.processedSamples++;
                return actual;
            }
        };
    }
}
