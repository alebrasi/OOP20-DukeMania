package it.dukemania.Model.serializers.song;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonDeserialize(using = SongDeserializer.class)
@JsonSerialize(using = SongSerializer.class)
public class SongInfo {
    private final String title;
    private final double  duration;
    private final List<TrackInfo> tracks;
    private final double bpm;
    private final String songHash;

    public SongInfo(final String title, final String songHash, final double duration, final List<TrackInfo> tracks,
            final double bpm) {
        this.title = title;
        this.duration = duration;
        this.tracks = tracks;
        this.bpm = bpm;
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
    public final List<TrackInfo> getTracks() {
        return tracks;
    }
    public final String getSongHash() {
        return songHash;
    }
}
