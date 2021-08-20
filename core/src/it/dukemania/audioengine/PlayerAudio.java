package it.dukemania.audioengine;

import com.badlogic.gdx.Gdx;
import it.dukemania.midi.AbstractNote;
import it.dukemania.midi.Song;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PlayerAudio implements Player {

    private final Engine audioEngine = new Engine();
    private final List<NoteIterator<AbstractNote>> tracks = new ArrayList<>();
    private long startMillis;

    /**
     * Create the audio player that will play a Song parsed froma midi file.
     * @param canzone the song
     */
    public PlayerAudio(final Song canzone) {

        canzone.getTracks().forEach(track -> {
            if (track.getChannel() == 10) {
                audioEngine.addDrum();
            } else {
                audioEngine.addSynth(track);
            }
            tracks.add(new NoteIterator<>() {
                private int index = 0;
                @Override
                public AbstractNote current() {
                    return track.getNotes().get(index);
                }
                @Override
                public boolean hasNext() {
                    return index < track.getNotes().size();
                }
                @Override
                public void increment() {
                    index++;
                }
                @Override
                public int getChannel() {
                    return track.getChannel();
                }
            });
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSongEnded() {
        return tracks.stream().noneMatch(NoteIterator::hasNext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playNotes() {

        this.startMillis = this.startMillis == 0 ? Instant.now().toEpochMilli() : this.startMillis;
        long current = Instant.now().toEpochMilli() - this.startMillis;
        tracks.stream().filter(NoteIterator::hasNext).forEach(track -> {
            if (track.hasNext() && track.current().getStartTime() / 1000 < current) {
                if (track.getChannel() == 10) {
                    DrumSynth syn = (DrumSynth) audioEngine.getSynth(tracks.indexOf(track));
                    syn.playPercussion((DrumSamples) track.current().getItem());
                } else {
                    KeyboardSynth syn = (KeyboardSynth) audioEngine.getSynth(tracks.indexOf(track));
                    syn.playTimedNote((Integer) track.current().getItem(), track.current().getDuration().orElse(1000L));
                }
                track.increment();
            }
        });
        audioEngine.playBuffer();

    }

}
