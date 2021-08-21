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


    public Instrument(final InstrumentType instrument) {
        this.instrument = instrument;
        try {
            this.list = new ConfigurationsModelImpl(
                    new StorageFactoryImpl().getConfigurationStorage()).readSynthesizersConfiguration();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            System.out.println("helo");
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

    private List<InstrumentType> calcAssociatedInstrumentType() throws IOException {
        /*Storage st = new StorageFactoryImpl().getConfigurationStorage();
        List<SynthInfo> list = new ConfigurationsModelImpl(st).readSynthesizersConfiguration();
        */
        return this.list.stream()
                .filter(s -> s.getSynth().equals(this.synthetizer))
                .findAny().orElseThrow().getAssociatedInstruments();
    }

    private String calcName() throws IOException {
        /*Storage st = new StorageFactoryImpl().getConfigurationStorage();
        List<SynthInfo> list = new ConfigurationsModelImpl(st).readSynthesizersConfiguration();
        */
        return this.list.stream()
                .filter(s -> s.getSynth().equals(this.synthetizer))
                .findAny().orElseThrow().getName();
    }


    private SynthBuilderImpl calcSynthetizer() throws IOException {
        /*
        Storage st = new StorageFactoryImpl().getConfigurationStorage();
        List<SynthInfo> list = new ConfigurationsModelImpl(st).readSynthesizersConfiguration();
        */
        return this.list.stream()
                .filter(s -> s.getAssociatedInstruments().contains(this.instrument))
                .map(s -> s.getSynth())
                .findAny().orElse(list.stream()
                        .filter(s -> s.getName().equals("default"))
                        .map(s -> s.getSynth())
                        .findAny().get());
    }


    public String getName() {
        return name;
    }
    public SynthBuilderImpl getSynthetizer() {
        return this.synthetizer;
    }
    public List<InstrumentType> getAssociatesInstrumentType() {
        return this.associatedInstrumentType;
    }

    public Enum<InstrumentType> getInstrument() {
        return this.instrument;
    }

}
