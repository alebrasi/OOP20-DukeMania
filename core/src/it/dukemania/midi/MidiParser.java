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

public final class MidiParser implements Parser {
    private static final int SET_TEMPO = 0X51;
    private static final int MICROSEC_PER_MIN = 60_000_000;
    private static MidiParser instance;

    private MidiParser() { }

    /**
     * this method makes sure that exist only one instance of midiParser and returns it.
     * @return an instance of MidiParser
     */
    public static MidiParser getInstance() {
        if (instance == null) {
            instance = new MidiParser();
        }
        return instance;
    }

    @Override
    public Song parse(final File myMidi) throws InvalidMidiDataException, IOException  {
        final Sequence sequence = MidiSystem.getSequence(myMidi);
        final double microsecPerTick = (double) sequence.getMicrosecondLength() / sequence.getTickLength();
        if (MidiSystem.getMidiFileFormat(myMidi).getType() == 2) {
            throw new InvalidMidiDataException();
        }
        final List<ParsedTrack> myTracks = new ArrayList<>();
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
        channelMap.forEach((k, v) -> myTracks.add(FactoryConfigurator.getFactory(k).createTrack(v.getX(), v.getY(), k)));
        //rimozione eventuali tracce vuote
        myTracks.removeIf(x -> x.getNotes().size() == 0);
        //rimozione eventuali note rimaste senza durata
        myTracks.forEach(t -> t.getNotes().removeIf(n -> n.getDuration().isEmpty()));
        myTracks.sort((t1, t2) -> t1.getChannel() - t2.getChannel());
        return new Song(myMidi.getName(), sequence.getMicrosecondLength(), myTracks, bpm);
    }


    /**
     * this method calculate time in microseconds.
     * @param tick the time expressed in midi tick for which we will calculate the time in microseconds
     * @param microsecPerTick the number of microseconds that compose a tick
     * @return time in microsec
     */
    private long calcTime(final long tick, final double microsecPerTick) {
        //System.out.println(tick + " " + microsecPerTick + " " + tick * microsecPerTick + " " + (long) (tick * microsecPerTick));
        return  (long) (tick * microsecPerTick);
    }

    /**
     * this method calculate bpm.
     * @param data the byte array that rapresent the bpm
     * @return bpm as a decimal double
     */
    private static double calcBpm(final byte[] data) {
        final ByteBuffer microsecPerBeat = ByteBuffer.allocate(Integer.BYTES);
        microsecPerBeat.position(microsecPerBeat.position() + 1);
        microsecPerBeat.put(data);
        return (double) MICROSEC_PER_MIN / microsecPerBeat.getInt(0);
    }

    /**
     * this method tells if the short message given is a message that contains a note or an instrument event.
     * @param sm the short message to analyze
     * @return a boolean which is true if the message is a note or instrument message
     */
    private static boolean isNoteOrInsrument(final MidiMessage sm) {
        final int type = ((ShortMessage) sm).getCommand();
        return type == ShortMessage.NOTE_ON || type == ShortMessage.NOTE_OFF || type == ShortMessage.PROGRAM_CHANGE;
    }


    /**
     * this method add a note to the given list.
     * @param sm the short message which contain the note event info
     * @param notes the list of notes in which the note will be added
     * @param factory the correct factory for the note creation
     * @param time the time in which the event take place in microseconds 
     */
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
