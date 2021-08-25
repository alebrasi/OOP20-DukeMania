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
     * @param instrument the InstrumentType associated to the Instrument.
     */
    public Instrument(final InstrumentType instrument) {
        this.instrument = instrument;
        try {
            this.list = new ConfigurationsModelImpl(
                    new StorageFactoryImpl().getConfigurationStorage()).readSynthesizersConfiguration();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        this.synthetizer = calcSynthetizer();
        this.name = calcName();
        this.associatedInstrumentType = calcAssociatedInstrumentType();
    }

    /**
     * this method find the instruments which use the same synthetizer as the current one. 
     * @return a list of instruments which use the same synthetizer as the current one.
     */
    private List<InstrumentType> calcAssociatedInstrumentType() {
        return this.list.stream()
                .filter(s -> s.getSynth().equals(this.synthetizer))
                .findAny().orElseThrow().getAssociatedInstruments();
    }

    /**
     * this method find the name associated to the synthetizer of this instrument.
     * @return the syntetizer name
     */
    private String calcName() {
        return this.list.stream()
                .filter(s -> s.getSynth().equals(this.synthetizer))
                .findAny().orElseThrow().getName();
    }


    /**
     * this method find which synthetizer is associated to this instrument.
     * @return the synthetizer associated to the instrument
     */
    private SynthBuilderImpl calcSynthetizer() {
        return this.list.stream()
                .filter(s -> s.getAssociatedInstruments().contains(this.instrument))
                .map(s -> s.getSynth())
                .findAny().orElse(list.stream()
                        .filter(s -> s.getName().equals("default"))
                        .map(s -> s.getSynth())
                        .findAny().get());
    }



    /**
     * this method returns the name associated to the synthetizer of this instrument.
     * @return the associated syntetizer name
     */
    public String getName() {
        return name;
    }

    /**
     * this method returns the synthetizer associated to this instrument.
     * @return the associated synthetizer
     */
    public SynthBuilderImpl getSynthetizer() {
        return this.synthetizer;
    }

    /**
     * this method return the instruments which use the same synthetizer as the current one. 
     * @return a list of related instruments
     */
    public List<InstrumentType> getAssociatesInstrumentType() {
        return this.associatedInstrumentType;
    }

    /**
     * this method return the enum name of the instrument.
     * @return an element of the enum InstrumentType
     */
    public Enum<InstrumentType> getInstrument() {
        return this.instrument;
    }

}
