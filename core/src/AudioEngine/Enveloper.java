package AudioEngine;

public class Enveloper {
    private final float atkVol;
    private final float step1;
    private final float step2;
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
        this.step2 = (1f / rel) / (Settings.SAMPLESPERMILLI * -1);
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
            private float actual = 0.01f;
            private float totalSamples = 0l;
            private long processedSamples = 0l;
            private float resetStep = 0;
            /**
             * {@inheritDoc}
             */
            @Override
            public void refresh(long ttl) {
                totalSamples = (float)ttl * Settings.SAMPLESPERMILLI;
                resetStep = actual / totalSamples;
                processedSamples = -10l;
            }
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
                return  this.processedSamples++ < 0 ? actual -= resetStep : this.processedSamples++ >= this.totalSamples ?
                        this.actual <= 0 ? 0 : (this.actual += step2) :
                        this.actual >= atkVol ? atkVol : (this.actual += step1);
            }
        };
    }
}
