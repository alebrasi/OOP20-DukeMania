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
import javax.sound.midi.MidiFileFormat;
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
        /*final*/ Sequence sequence = MidiSystem.getSequence(myMidi);



        MidiFileFormat mff = null;
        try {
            mff = MidiSystem.getMidiFileFormat(myMidi);
        } catch (InvalidMidiDataException e) {
            //  Auto-generated catch block
            System.out.println("e");
            e.printStackTrace();
        } catch (IOException e) {
            //  Auto-generated catch block
            System.out.println("f");
            e.printStackTrace();
        }

        if (mff.getType() == 0) {
            sequence = adapter(sequence);
        }






        final double microsecPerTick = sequence.getMicrosecondLength() / sequence.getTickLength();

        final List<TrackInterface> myTracks = new ArrayList<>();
        double bpm = 0;
        for (final Track t : sequence.getTracks()) {
            final List<AbstractNote> notes = new ArrayList<>(); // inizializzo un collection di note e un enum di instrument
            Enum<InstrumentType> instrument = null;
            Optional<Integer> channel = Optional.empty();
            Optional<AbstractFactory> factory = Optional.empty();
            for (int index = 0; index < t.size(); index++) {                                // prendo ogni evento della traccia
                final MidiEvent event = t.get(index);
                final MidiMessage message = event.getMessage();
                if (message instanceof MetaMessage && ((MetaMessage) message).getType() == SET_TEMPO) {
                    bpm = calcBpm(((MetaMessage) message).getData());
                } else if (message instanceof ShortMessage) {
                    final ShortMessage shortMessage = (ShortMessage) message;
                    final int messageType = shortMessage.getCommand();
                    //System.out.print(sm.getChannel()+1);
                    if ((messageType == ShortMessage.NOTE_ON || messageType == ShortMessage.NOTE_OFF) 
                            /*&& isValid(shortMessage)*/) {
                        addNote(shortMessage, notes, calcTime(event.getTick(), microsecPerTick),
                                factory.orElse(FactoryConfigurator.getFactory(shortMessage.getChannel() + 1)));
                        //System.out.println("nota" + (shortMessage.getChannel() + 1) + " " + channel.get());
                    } else if (messageType == ShortMessage.PROGRAM_CHANGE) {
                        channel = Optional.of(shortMessage.getChannel() + 1);
                        factory = Optional.of(FactoryConfigurator.getFactory(channel.get()));
                        instrument = InstrumentType.values()[shortMessage.getData1()];
                        //instrument= channel.get() == PERCUSSION_TRACK ? null : InstrumentType.values()[shortMessage.getData1()];
                        //TODO chiedi per eleganza soluzione
                        //System.out.println("cambio strumento" + instrument + channel.get());
                    } else {
                        //System.out.println("hope" + shortMessage.getChannel() + " " + shortMessage.getCommand());
                    }
                } else {
                    //System.out.println("something");
                }
            }
            // commentato per lettura output
            /*
            notes.forEach(n -> System.out.println(n.getStartTime() + " " + n.getDuration().orElse((double) 0.0) 
                    + " " + n.getIdentifier() ));
                    //+ " " + (n.getClass() == Note.class ? ((Note) n).getFrequency() : ((DrumSound) n).getInstrument())));
            System.out.println();
            */
            final TrackInterface track = factory.get().createTrack(instrument, notes, channel.get());
            //final TrackImpl track = new TrackImpl(instrument, notes, channel.get());  // creo la mytrack da aggiungere alla song
            myTracks.add(track);                                        // la agggiungo
        }
        // CREO LA SONG
        myTracks.removeIf(x -> x.getNotes().size() == 0);
        myTracks.sort((t1, t2) -> t1.getChannel() - t2.getChannel());
        final Song song = new Song(myMidi.getName(), sequence.getMicrosecondLength(), bpm, "hash");    // per titolo da nomefile
        song.setTracks(myTracks);
        return song;
    }


/*
    private boolean isValid(final ShortMessage sm) {
        return sm.getChannel() + 1 != PERCUSSION_TRACK || sm.getData1() >= MIN && sm.getData1() <= MAX;
        //TODO da spostare
    }
    */


    private int calcTime(final long tick, final double microsecPerTick) {
        return  (int) (tick * microsecPerTick);
    }
    // TODO passa tutto a long


    private static double calcBpm(final byte[] data) {
        final ByteBuffer microsecPerBeat = ByteBuffer.allocate(Integer.BYTES);
        microsecPerBeat.position(microsecPerBeat.position() + 1);
        microsecPerBeat.put(data);
        return (double) MICROSEC_PER_MIN / microsecPerBeat.getInt(0);
    }



    private static void addNote(final ShortMessage sm, final List<AbstractNote> notes, final int time,
            final AbstractFactory factory) {
        final int data = sm.getData1();
        if (sm.getCommand() == ShortMessage.NOTE_ON && sm.getData2() != 0) {
            // metto la nota parziale
            try {
                notes.add(factory.createNote(Optional.empty(), time, data));
            } catch (InvalidNoteException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                //System.out.println("errore" + notes.get(notes.size() - 1));
            }
            //notes.remove(null);
            //TODO chiedi per eleganza soluzione
        } else {
            // tolgo la nota parziale e la sostituisco con la completa
            final Optional<AbstractNote> n0 = notes.stream()
                                            .filter(n -> n.getDuration().isEmpty() && n.getIdentifier() == data)
                                            .findAny();
            if (n0.isPresent()) {
                notes.set(notes.indexOf(n0.get()), factory.createNote(
                        Optional.of((double) (time - n0.get().getStartTime())), n0.get().getStartTime(), data));
                //TODO ask for int-int=double -> long
                /*
                notes.remove(n0.get());
                notes.add(factory.createNote(
                Optional.of((double) (time - n0.get().getStartTime())), n0.get().getStartTime(), data));
                */
                //TODO si puo cancellare
                notes.sort((n1, n2) -> n1.getStartTime() - n2.getStartTime());
            }
        }
    }



//TODO fanculo alla factory, fai direttamente track che estende percussiontrack
    // metti la mappa e sistema i canali (no adapter)
    // 





private Sequence adapter(final Sequence sequence) {
        //TODO
        return sequence;
    }


}
