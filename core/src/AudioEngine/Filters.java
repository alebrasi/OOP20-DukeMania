package AudioEngine;

public class Filters {
    // TODO RENAME, ITS NOT AN ACTUAL FACTORY

    public static void sampleNHold(float [] buff, long sampToHold){
        float actualSample = 0;
        for(int i=0;i<buff.length;i++){
            if(i % sampToHold == 0){
                actualSample = buff[i];
            }else{
                buff[i] = actualSample;
            }
        }
    }

    public static void frequencyModulation(double [] carrier, double [] modulator, float carrierFreq){
        float period = Settings.SAMPLE_RATE / carrierFreq;
        if(carrier.length > modulator.length){
            return;
        }
        for(float i =0;i<carrier.length;i++){
            carrier[(int) i] = (float) (1*(Math.sin(2.0 * Math.PI * carrier[(int) i] * period + 1 * Math.sin(2*Math.PI*modulator[(int) i]*period))));
        }
    }

}
