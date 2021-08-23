package it.dukemania.midi;

import java.io.IOException;
import java.util.List;

import it.dukemania.Model.serializers.ConfigurationsModelImpl;
import it.dukemania.Model.serializers.synthesizer.SynthInfo;
import it.dukemania.audioengine.SynthBuilderImpl;
import it.dukemania.util.storage.StorageFactoryImpl;

public final class Instrument {
    private InstrumentType instrument;
    private String name;
    private SynthBuilderImpl synthetizer;
    private List<InstrumentType> associatedInstrumentType;
    private List<SynthInfo> list;


    /**
     * this is the constructor.
     * @param instrument
     */
    public Instrument(final InstrumentType instrument) {
        this.instrument = instrument;
        try {
            this.list = new ConfigurationsModelImpl(
                    new StorageFactoryImpl().getConfigurationStorage()).readSynthesizersConfiguration();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        try {
            this.synthetizer = calcSynthetizer();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            this.name = calcName();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            this.associatedInstrumentType = calcAssociatedInstrumentType();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * this method find the instruments which use the same synthetizer as the current one. 
     * @return a list of instruments
     * @throws IOException
     */
    private List<InstrumentType> calcAssociatedInstrumentType() throws IOException {
        return this.list.stream()
                .filter(s -> s.getSynth().equals(this.synthetizer))
                .findAny().orElseThrow().getAssociatedInstruments();
    }

    /**
     * this method find the name associated to the synthetizer for this instrument.
     * @return the syntetizer name
     * @throws IOException
     */
    private String calcName() throws IOException {
        return this.list.stream()
                .filter(s -> s.getSynth().equals(this.synthetizer))
                .findAny().orElseThrow().getName();
    }


    /**
     * this method find which synthetizer is associated to this instrument.
     * @return the synthetizer
     * @throws IOException
     */
    private SynthBuilderImpl calcSynthetizer() throws IOException {
        return this.list.stream()
                .filter(s -> s.getAssociatedInstruments().contains(this.instrument))
                .map(s -> s.getSynth())
                .findAny().orElse(list.stream()
                        .filter(s -> s.getName().equals("default"))
                        .map(s -> s.getSynth())
                        .findAny().get());
    }



    /**
     * this method returns the name associated to the synthetizer for this instrument.
     * @return the syntetizer name
     * @throws IOException
     */
    public String getName() {
        return name;
    }

    /**
     * this method returns the synthetizer associated to this instrument.
     * @return the synthetizer
     * @throws IOException
     */
    public SynthBuilderImpl getSynthetizer() {
        return this.synthetizer;
    }

    /**
     * this method return the instruments which use the same synthetizer as the current one. 
     * @return a list of instruments
     * @throws IOException
     */
    public List<InstrumentType> getAssociatesInstrumentType() {
        return this.associatedInstrumentType;
    }

    /**
     * this method return the instrument.
     * @return an instrument
     */
    public Enum<InstrumentType> getInstrument() {
        return this.instrument;
    }

}
