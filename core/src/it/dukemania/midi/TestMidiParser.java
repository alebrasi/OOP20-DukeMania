package it.dukemania.midi;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import org.junit.jupiter.api.Test;

class TestMidiParser {

    /**
     * this method create a MIDI file.
     * @param nTracks
     * @param type
     * @return a MIDI file
     * @throws InvalidMidiDataException
     * @throws IOException
     */
    File createFile(final int nTracks, final int type) throws InvalidMidiDataException, IOException {
        Sequence seq = new Sequence((float) 0.0, 1, nTracks);
        List<Track> tracks = new ArrayList<>();
        Collections.addAll(tracks, seq.getTracks());
        tracks.get(0).add(new MidiEvent(new MetaMessage(0X51, new byte[] {(byte) 0x07, (byte) 0xA1, (byte) 0x20 }, 3), 0L));
        int ch = 0;
        int instrument = 3;
        for (Track t : tracks) { 
            t.add(new MidiEvent(new ShortMessage(ShortMessage.PROGRAM_CHANGE, ch, instrument + ch, 0), 1L));
            t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, ch, 69 + ch, 7), 2L));
            t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, ch, 69 + ch, 7), 3L));
            ch = ch == 2 ? 9 : ch + 1;
            t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, ch, 66 + ch, 7), 3L));
            t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, ch, 41 + ch, 7), 4L));
            t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, ch, 66 + ch, 0), 4L));
            t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, ch, 41 + ch, 0), 6L));
            t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, ch, 69 + ch, 7), 4L));
            t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, ch, 69 + ch, 7), 6L));
        }
        File f = new File("File.mid");
        MidiSystem.write(seq, type, f);
        //System.out.println(MidiSystem.isFileTypeSupported(2,seq));
        return f;
    }


    /**
     * this test makes sure that the midi parser works.
     * @throws InvalidMidiDataException
     * @throws IOException
     */
    @Test
    void testMidiParser() throws InvalidMidiDataException, IOException {
        //int size = 3;
        final File file1 = createFile(3, 1);
        Parser mp = MidiParser.getInstance();
        Song song1 = mp.parse(file1);
        assertEquals(song1.getBPM(), 120, 0.00001);
        assertEquals(song1.getTracks().size(), 4);
        assertEquals(song1.getTracks().get(0).getNotes().size(), 1);
        assertEquals(song1.getTracks().get(1).getNotes().size(), song1.getTracks().get(2).getNotes().size(), 3);
        assertEquals(song1.getTracks().get(3).getNotes().size(), 3);
        song1.getTracks().stream()
                         .filter(t -> t.getChannel() != 10)
                         .forEach(t -> assertTrue(t instanceof KeyboardTrack));
        List<KeyboardTrack> tracks = song1.getTracks().stream()
                                                    .filter(t -> t.getChannel() != 10)
                                                    .map(t -> (KeyboardTrack) t)
                                                    .collect(Collectors.toList());
        assertEquals(tracks.get(0).getInstrument(), InstrumentType.HONKY_TONK_PIANO);
        assertEquals(tracks.get(1).getInstrument(), InstrumentType.ELECTRIC_PIANO_1);
        assertEquals(tracks.get(2).getInstrument(), InstrumentType.ELECTRIC_PIANO_2);
        assertEquals(new Instrument((InstrumentType) tracks.get(2).getInstrument()).getInstrument(),
                InstrumentType.ELECTRIC_PIANO_2);
        assertNotNull(new Instrument((InstrumentType) tracks.get(2).getInstrument()).getSynthetizer());
        assertNotNull(new Instrument((InstrumentType) tracks.get(2).getInstrument()).getAssociatesInstrumentType());
        assertNotNull(new Instrument((InstrumentType) tracks.get(2).getInstrument()).getName());
        tracks.forEach(t -> t.getNotes().forEach(n -> assertTrue(n instanceof Note)));
        assertTrue(((Note) tracks.get(1).getNotes().get(0)).getFrequency() > 466 &&
                ((Note) tracks.get(1).getNotes().get(0)).getFrequency() < 467);
        tracks.get(0).getNotes().forEach(n -> assertEquals(n.getItem(), 69));
        assertEquals(tracks.get(1).getNotes().get(0).getDuration(), tracks.get(1).getNotes().get(1).getDuration());
        assertEquals(tracks.get(1).getNotes().get(2).getStartTime(), tracks.get(1).getNotes().get(1).getDuration().get() 
                + tracks.get(1).getNotes().get(1).getStartTime(), 0.00001);
        assertEquals(song1.getDuration(), tracks.get(1).getNotes().get(2).getDuration().get() 
                + tracks.get(1).getNotes().get(2).getStartTime(), 0.00001);
        assertTrue(song1.getTracks().get(3) instanceof PercussionTrack);
        song1.getTracks().get(3).getNotes().forEach(n -> assertTrue(n instanceof PercussionNote));
        assertEquals(((PercussionNote) song1.getTracks().get(3).getNotes().get(0)).getInstrument(), Percussion.CLAVES);
        assertEquals(((PercussionNote) song1.getTracks().get(3).getNotes().get(1)).getInstrument(), Percussion.HIGH_TOM);
        assertEquals(tracks.get(1).getNotesMaxDuration(), new HashMap<>(Map.of(
            tracks.get(1).getNotes().get(1).getIdentifier(), tracks.get(1).getNotes().get(1).getDuration().get(),
            tracks.get(1).getNotes().get(2).getIdentifier(), tracks.get(1).getNotes().get(2).getDuration().get(),
            tracks.get(1).getNotes().get(3).getIdentifier(), tracks.get(1).getNotes().get(3).getDuration().get())
        ));
        final File file2 = createFile(1, 0);
        Song song2 = mp.parse(file2);
        assertEquals(song1.getTitle(), song2.getTitle());
        assertEquals(song2.getTracks().size(), 2);
        assertEquals(song2.getTracks().get(0).getNotes().size(), 1);
        assertEquals(song2.getTracks().get(1).getNotes().size(), 3);
    }

}
