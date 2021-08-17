package it.dukemania.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dukemania.Model.serializers.SongDeserializer;
import it.dukemania.Model.serializers.SongSerializer;
import it.dukemania.Model.serializers.TrackDeserializer;
import it.dukemania.Model.serializers.TrackSerializer;
import it.dukemania.midi.InstrumentType;

@JsonDeserialize(using = TrackDeserializer.class)
@JsonSerialize(using = TrackSerializer.class)
public class TrackInfo {
    private final String trackName;
    private final InstrumentType instrument;
    private final int trackID;

    public TrackInfo(final int channel, final String trackName, final InstrumentType instrument) {
        this.trackName = trackName;
        this.instrument = instrument;
        this.trackID = channel;
    }

    public String getTrackName() {
        return trackName;
    }

    public Enum<InstrumentType> getInstrument() {
        return instrument;
    }

    public int getChannel() {
        return trackID;
    }
}
