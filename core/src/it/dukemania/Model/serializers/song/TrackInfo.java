package it.dukemania.Model.serializers.song;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
