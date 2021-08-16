package it.dukemania.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dukemania.Model.serializers.SongDeserializer;
import it.dukemania.Model.serializers.SongSerializer;

import java.util.Collection;
import java.util.List;

@JsonDeserialize(using = SongDeserializer.class)
@JsonSerialize(using = SongSerializer.class)
public class SongConfiguration {
    private final String title;
    private final double  duration;
    private final List<MyTrack> tracks;
    private final double bpm;
    private final String songHash;

    public SongConfiguration(final String title, final String songHash, final double duration, final List<MyTrack> tracks, final double BPM) {
        this.title = title;
        this.duration = duration;
        this.tracks = tracks;
        this.bpm = BPM;
        this.songHash = songHash;
    }

    public final String getTitle() {
        return title;
    }
    public final double getBPM() {
        return bpm;
    }
    public final double getDuration() {
        return duration;
    }
    public final List<MyTrack> getTracks() {
        return tracks;
    }
    public final String getSongHash() {
        return songHash;
    }
}
