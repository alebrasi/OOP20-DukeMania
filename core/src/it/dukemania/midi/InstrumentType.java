package it.dukemania.midi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import it.dukemania.Model.serializers.ConfigurationsModelImpl;
import it.dukemania.Model.serializers.synthesizer.SynthInfo;
import it.dukemania.audioengine.SynthBuilderImpl;
import it.dukemania.util.storage.Storage;
import it.dukemania.util.storage.StorageFactoryImpl;

public enum InstrumentType {

    /**
     * Piano.
     */
    ACOUSTIC_GRAND_PIANO, BRIGHT_ACOUSTIC_PIANO, ELECTRIC_GRAND_PIANO, HONKY_TONK_PIANO, ELECTRIC_PIANO_1, ELECTRIC_PIANO_2, HARPSICHORD, CLAVINET, 
    /** 
     * Chromatic Percussion.
     */
    CELESTA, GLOCKENSPIEL, MUSIC_BOX, VIBRAPHONE, MARIMBA, XYLOPHONE, TUBULAR_BELLS, DULCIMER, 
    /**
     * Organ.
     */
    DRAWBAR_ORGAN, PERCUSSIVE_ORGAN, ROCK_ORGAN, CHURCH_ORGAN, REED_ORGAN, ACCORDION, HARMONICA, TANGO_ACCORDION, 
    /**
     * Guitar.
    */
    ACOUSTIC_GUITAR_N, ACOUSTIC_GUITAR_S, ELECTRIC_GUITAR_J, ELECTRIC_GUITAR_C, ELECTRIC_GUITAR_M, OVERDRIVEN_GUITAR, DISTORTION_GUITAR, GUITAR_ARMONICS,
    /**
     * Bass.
     */
    ACOUSTIC_BASS, ELECTRIC_BASS_F, ELECTRIC_BASS_P, FRETLESS_BASS, SLAP_BASS_1, SLAP_BASS_2, SYNTH_BASS_1, SYNTH_BASS_2,
    /**
     * Strings.
     */
    VIOLIN, VIOLA, CELLO, CONTRABASS, TREMOLO_STRINGS, PIZZICATO_STRINGS, ORCHESTRAN_HARP, TIMPANI,
    /**
     * Ensemble.
     */
    STRING_ENSEMBLE_1, STRING_ENSEMBLE_2, SYNTH_STRINGS_1, SYNTH_STRINGS_2, CHOIR_AAHS, VOICE_OOHS, SYNTH_VOICE, ORCHESTRA_HIT, 
    /**
     *  Brass.
     */
    TRUMPET, TROMBONE, TUBA, MUTED_TRUMPET, FRENCH_HORN, BRASS_SECTION, SYNTH_BRASS_1, SYNTH_BRASS_2,
    /**
     * Reed.
     */
    SOPRANO_SAX, ALTO_SAX, TENOR_SAX, BARITONE_SAX, OBOE, ENGLISH_HORN, BASSOON, CLARINET,
    /**
     * Pipe.
     */
    PICCOLO, FLUTE, RECORDER, PAN_FLUTE, BLOWN_BOTTLE, SHAKUHACHI, WHISTLE, OCARINA, 
    /**
     * Synth Lead.
     */
    LEAD_1, LEAD_2, LEAD_3, LEAD_4, LEAD_5, LEAD_6, LEAD_7, LEAD_8, 
    /**
     * Synth Pad.
     */
    PAD_1, PAD_2, PAD_3, PAD_4, PAD_5, PAD_6, PAD_7, PAD_8, 
    /**
     * Synth Effects.
     */
    FX_1, FX_2, FX_3, FX_4, FX_5, FX_6, FX_7, FX_8, 
    /**
     * Ethnic.
     */
    SITAR, BANJO, SHAMISEN, KOTO, KALIMBA, BAG_PIPE, FIDDLE, SHANAI,
    /**
     * Percussive.
     */
    TINKLE_BELL, AGOGO, STEEL_DRUMS, WOODBLOCK, TAIKOP_DRUM, MELODIC_TOM, SYNT_DRUM, REVERSE_CYMBAL,
    /**
     * Sound Effects.
     */
    GUITAR_FRET_NOISE, BREATH_NOISE, SEASHORE, BIRD_TWEET, TELEPHONE_RING, HELICOPTER, APPLAUSE, GUNSHOT;

}
