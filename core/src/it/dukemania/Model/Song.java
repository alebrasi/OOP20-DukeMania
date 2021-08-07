package it.dukemania.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dukemania.Model.serializers.SongDeserializer;
import it.dukemania.Model.serializers.SongSerializer;

import java.util.Collection;

@JsonDeserialize(using = SongDeserializer.class)
@JsonSerialize(using = SongSerializer.class)
public class Song {
    private final String title;
    private final double  duration;
    private final Collection<MyTrack> tracks;
    private final double bpm;

    public Song(final String title, final double duration, final Collection<MyTrack> tracks, final double BPM) {
        this.title = title;
        this.duration = duration;
        this.tracks = tracks;
        bpm = BPM;
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
    public final Collection<MyTrack> getTracks() {
        return tracks;
    }
}
