package AudioEngine;

import java.util.Iterator;

public class Enveloper {
    private final float atkVol;
    private final float step1;
    private final float step2;

    public Enveloper(long atk, float atk_vol, long rel){
        this.atkVol = atk_vol;
        this.step1 = (atk_vol / atk) / Settings.SAMPLESPERSEC;
        this.step2 = (1f / rel) / (Settings.SAMPLESPERSEC * -1);
    }

    public Iterator<Float> createEnveloper(Long ttl){
        return new Iterator<Float>() {
            float actual = 0.01f;
            final float totalTime = (float) ttl * Settings.SAMPLESPERSEC;
            long processedSamples = 0L;

            @Override
            public boolean hasNext() {
                return this.actual > 0;
            }

            @Override
            public Float next() {
                return this.processedSamples++ >= this.totalTime ?
                        this.actual <= 0 ? 0 : (this.actual += step2) :
                        this.actual >= atkVol ? atkVol : (this.actual += step1);
            }
        };
    }
}
