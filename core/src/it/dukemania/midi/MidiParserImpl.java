package it.dukemania.midi;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MidiParserImpl implements MidiParser {
    private static final int SET_TEMPO = 0X51;
    private static final int MICROSEC_PER_MIN = 60_000_000;
    private static final int PERCUSSION_TRACK = 10;
    private static final int MIN = 35;
    private static final int MAX = 81;


    @Override
    public final Song parseMidi(final String file) throws InvalidMidiDataException, IOException  {
        final File myMidi = new File(file);
        final Sequence sequence = MidiSystem.getSequence(myMidi);
        final double microsecPerTick = sequence.getMicrosecondLength() / sequence.getTickLength();


        final List<MyTrack> myTracks = new ArrayList<>();
        double bpm = 0;
        for (final Track t : sequence.getTracks()) {
            final List<Note> notes = new ArrayList<>(); // inizializzo un collection di note e un enum di instrument
            Enum<InstrumentType> instrument = null;
            Optional<Integer> channel = Optional.empty();
            for (int index = 0; index < t.size(); index++) {                                // prendo ogni evento della traccia
                final MidiEvent event = t.get(index);
                final MidiMessage message = event.getMessage();
                if (message instanceof MetaMessage && ((MetaMessage) message).getType() == SET_TEMPO) {
                    bpm = calcBpm(((MetaMessage) message).getData());
                } else if (message instanceof ShortMessage) {
                    final ShortMessage shortMessage = (ShortMessage) message;
                    final int messageType = shortMessage.getCommand();
                    //System.out.print(sm.getChannel()+1);
                    if ((messageType == ShortMessage.NOTE_ON || messageType == ShortMessage.NOTE_OFF) && isValid(shortMessage)) {
                        addNote(shortMessage, notes, calcTime(event.getTick(), microsecPerTick),
                                channel.orElse(shortMessage.getChannel() + 1));
                    } else if (messageType == ShortMessage.PROGRAM_CHANGE) {
                        channel = Optional.of(shortMessage.getChannel() + 1);
                        instrument = channel.get() == PERCUSSION_TRACK ? null : InstrumentType.values()[shortMessage.getData1()];
                    }
                }
            }
            // commentato per lettura output
            /*
            notes.forEach(n -> System.out.println(n.getStartTime() + " " + n.getDuration().orElse((double) 0.0) 
                    + " " + n.getIdentifier() ));
                    //+ " " + (n.getClass() == Note.class ? ((Note) n).getFrequency() : ((DrumSound) n).getInstrument())));
            System.out.println();
            */
            final MyTrack track = new MyTrack(instrument, notes, channel.get());       // creo la mytrack da aggiungere alla song
            myTracks.add(track);                                        // la agggiungo
        }
        // CREO LA SONG
        myTracks.removeIf(x -> x.getNotes().size() == 0);
        myTracks.sort((t1, t2) -> t1.getChannel() - t2.getChannel());
        return new Song(myMidi.getName(), sequence.getMicrosecondLength(), myTracks, bpm);    // per titolo da nomefile....
    }


    private boolean isValid(final ShortMessage sm) {
        return sm.getChannel() + 1 != PERCUSSION_TRACK || sm.getData1() >= MIN && sm.getData1() <= MAX;
    }


    private int calcTime(final long tick, final double microsecPerTick) {
        return  (int) (tick * microsecPerTick);
    }


    private static double calcBpm(final byte[] data) {
        final ByteBuffer microsecPerBeat = ByteBuffer.allocate(Integer.BYTES);
        microsecPerBeat.position(microsecPerBeat.position() + 1);
        microsecPerBeat.put(data);
        return MICROSEC_PER_MIN / microsecPerBeat.getInt(0);    // divisione tra interi (va bene il bpm arrotondato a int?)
        // TODO
    }



    private static void addNote(final ShortMessage sm, final List<Note> notes, final int time, final int channel) {
        final int data = sm.getData1();
        if (sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() != 0) {
            // metto la nota parziale
            notes.add(channel == PERCUSSION_TRACK ? new PercussionNote(Optional.empty(), time, data) 
                    : new GenericNote(Optional.empty(), time, data));
        } else {
            // tolgo la nota parziale e la sostituisco con la completa
            final Optional<Note> n0 = notes.stream()
                                            .filter(n -> n.getDuration().isEmpty() && n.getIdentifier() == data)
                                            .findAny();
            if (n0.isPresent()) {
                notes.remove(n0.get());
                notes.add(channel == PERCUSSION_TRACK 
                      ? new PercussionNote(Optional.of((double) (time - n0.get().getStartTime())), n0.get().getStartTime(), data) 
                      : new GenericNote(Optional.of((double) (time - n0.get().getStartTime())), n0.get().getStartTime(), data));
                notes.sort((n1, n2) -> n1.getStartTime() - n2.getStartTime());
            }
        }
    }


}
