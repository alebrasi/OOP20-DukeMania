package it.dukemania.midi;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import it.dukemania.audioengine.Pair;

public class MidiParserImpl implements MidiParser {
    private static final int SET_TEMPO = 0X51;
    private static final int MICROSEC_PER_MIN = 60_000_000;


    @Override
    public final Song parseMidi(final File myMidi) throws InvalidMidiDataException, IOException  {
        final Sequence sequence = MidiSystem.getSequence(myMidi);
        final double microsecPerTick = (double) sequence.getMicrosecondLength() / sequence.getTickLength();

        final List<MidiTrack> myTracks = new ArrayList<>();
        final Map<Integer, Pair<Enum<InstrumentType>, List<AbstractNote>>> channelMap = new HashMap<>();
        double bpm = 0;
        for (final Track t : sequence.getTracks()) {
            for (int index = 0; index < t.size(); index++) {                    // prendo ogni evento della traccia
                final MidiEvent event = t.get(index);
                final MidiMessage message = event.getMessage();
                if (message instanceof MetaMessage && ((MetaMessage) message).getType() == SET_TEMPO) {
                    bpm = calcBpm(((MetaMessage) message).getData());
                } else if (message instanceof ShortMessage && isNoteOrInsrument(message)) {
                    final ShortMessage shortMessage = (ShortMessage) message;
                    final int channel = shortMessage.getChannel() + 1;
                    channelMap.putIfAbsent(channel, new Pair<>(null, new ArrayList<>()));
                    if (shortMessage.getCommand() == ShortMessage.PROGRAM_CHANGE) {
                        channelMap.put(channel, new Pair<>(InstrumentType.values()[shortMessage.getData1()], 
                                channelMap.get(channel).getY()));
                    } else {
                        addNote(shortMessage, channelMap.get(channel).getY(), FactoryConfigurator.getFactory(channel),
                                calcTime(event.getTick(), microsecPerTick));
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
        }
        // CREO LA SONG
        //TODO nel test controlla come si comporta con due tracce diverse con lo stesso canale
        channelMap.forEach((k, v) -> myTracks.add(FactoryConfigurator.getFactory(k).createTrack(v.getX(), v.getY(), k)));
        myTracks.removeIf(x -> x.getNotes().size() == 0);
        myTracks.sort((t1, t2) -> t1.getChannel() - t2.getChannel());
        return new Song(myMidi.getName(), sequence.getMicrosecondLength(), myTracks, bpm);    // per titolo da nomefile
    }



    private long calcTime(final long tick, final double microsecPerTick) {
        //System.out.println(tick + " " + microsecPerTick + " " + tick * microsecPerTick + " " + (long) (tick * microsecPerTick));
        return  (long) (tick * microsecPerTick);
    }


    private static double calcBpm(final byte[] data) {
        final ByteBuffer microsecPerBeat = ByteBuffer.allocate(Integer.BYTES);
        microsecPerBeat.position(microsecPerBeat.position() + 1);
        microsecPerBeat.put(data);
        return (double) MICROSEC_PER_MIN / microsecPerBeat.getInt(0);
    }

    private static boolean isNoteOrInsrument(final MidiMessage sm) {
        final int type = ((ShortMessage) sm).getCommand();
        return type == ShortMessage.NOTE_ON || type == ShortMessage.NOTE_OFF || type == ShortMessage.PROGRAM_CHANGE;
    }


    private static void addNote(final ShortMessage sm, final List<AbstractNote> notes, final AbstractFactory factory, 
            final long time) {
        final int data = sm.getData1();
        if (sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() != 0) {
            // metto la nota parziale
            try {
                notes.add(factory.createNote(Optional.empty(), time, data));
                //notes.add(factory.createNote(Optional.empty(), time, data));
            } catch (InvalidNoteException e) {
            }
        } else {
            // tolgo la nota parziale e la sostituisco con la completa
            final Optional<AbstractNote> n0 = notes.stream()
                                            .filter(n -> n.getDuration().isEmpty() && n.getIdentifier() == data)
                                            .findAny();
            if (n0.isPresent()) {
                notes.set(notes.indexOf(n0.get()), factory.createNote(
                        Optional.of((long) (time - n0.get().getStartTime())), n0.get().getStartTime(), data));
                notes.sort((n1, n2) -> (int) (n1.getStartTime() - n2.getStartTime()));
            }
        }
    }

}
