package it.dukemania.audioengine;

import it.dukemania.midi.Song;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PlayerAudio implements Player {

    private final Engine audioEngine = new Engine();
    private final List<PlayableTrack> playableTracks = new ArrayList<>();
    private long startMillis;

    /**
     * Create the audio player that will play a Song parsed from a MIDI file.
     * @param song the song
     */
    public PlayerAudio(final Song song) {

        song.getTracks().forEach(track -> playableTracks.add(new PlayableTrack() {

            private final Synth synthesizer = track.getChannel() == 10 ? audioEngine.addDrum() : audioEngine.addSynth(track);
            private int curr;

            @Override
            public boolean hasNext() {
                return curr < track.getNotes().size();
            }

            @Override
            public void update(final long millis) {
                if (track.getNotes().get(curr).getStartTime() / 1000 < millis) {
                    if (synthesizer instanceof DrumSynth) {
                        ((DrumSynth) synthesizer).playPercussion((DrumSamples) track.getNotes().get(curr).getItem());
                    } else {
                        ((KeyboardSynth) synthesizer).playTimedNote((Integer) track.getNotes().get(curr).getItem(),
                                track.getNotes().get(curr).getDuration()
                                .orElse(1000L));
                    }
                    curr++;
                }
            }
        }));
    }

    private void removeEndedTracks() {
        playableTracks.removeIf(x -> !x.hasNext());
    }

    @Override
    public final boolean playNotes() {
        this.startMillis = this.startMillis == 0 ? Instant.now().toEpochMilli() : this.startMillis;
        removeEndedTracks();
        playableTracks.forEach(x -> x.update(Instant.now().toEpochMilli() - this.startMillis));
        return audioEngine.playBuffer() == 0 && playableTracks.size() == 0;
    }

}
