package it.dukemania.midi;

import java.util.Arrays;

import it.dukemania.audioengine.DrumSamples;

public enum Percussion {

    ACOUSTIC_BASS_DRUM, BASS_DRUM_1, SIDE_STICK, ACOUSTIC_SNARE, HAND_CLAP, ELECTRIC_SNARE, LOW_FLOOR_TOM, CLOSED_HI_HAT
    , HIGH_FLOOR_TOM, PEDAL_HI_HAT, LOW_TOM, OPEN_HI_HAT, LOW_MID_TOM, HI_MID_TOM, CRASH_CYMBAL_1, HIGH_TOM, RIDE_CYMBAL_1
    , CHINESE_CYMBAL, RIDE_BELL, TAMBOURINE, SPLASH_CYMBAL, COWBELL, CRASH_CYMBAL_2, VIBRASLAP, RIDE_CYMBAL_2, HI_BONGO
    , LOW_BONGO, MUTE_HI_CONGA, OPEN_HI_CONGA, LOW_CONGA, HIGH_TIMBALE, LOW_TIMBALE, HIGH_AGOGO, LOW_AGOGO, CABASA, MARACAS
    , SHORT_WHISTLE, LONG_WHISTLE, SHORT_GUIRO, LONG_GUIRO, CLAVES, HI_WOOD_BLOCK, LOW_WOOD_BLOCK, MUTE_CUICA, OPEN_CUICA
    , MUTE_TRIANGLE, OPEN_TRIANGLE;

    private DrumSamples associated;

    static {
        Arrays.stream(Percussion.values()).forEach(drum -> drum.associated = Arrays.stream(DrumSamples.values())
                .filter(x -> drum.name().toUpperCase().contains(x.name().toUpperCase()))
                .findFirst().orElse(DrumSamples.Empty));
    }

    /**
     * this method return the associated DrumSample for each Percussion.
     * @return the associated drum
     */
    public DrumSamples  getAssociated() {
        return associated;
    }

}
