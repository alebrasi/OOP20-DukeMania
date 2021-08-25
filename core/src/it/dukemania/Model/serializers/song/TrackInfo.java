package it.dukemania.Model.serializers.song;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dukemania.Controller.logic.DifficultyLevel;
import it.dukemania.midi.InstrumentType;

@JsonDeserialize(using = TrackDeserializer.class)
@JsonSerialize(using = TrackSerializer.class)
public class TrackInfo {
    private String trackName;
    private InstrumentType instrument;
    private final int trackID;
    private final DifficultyLevel difficulty;


    public TrackInfo(final int channel, final String trackName, final InstrumentType instrument, 
            final DifficultyLevel difficulty) {
        this.trackName = trackName;
        this.instrument = instrument;
        this.trackID = channel;
        this.difficulty = difficulty;
    }

    /**
     * @return The difficulty level of the track
     */
    public DifficultyLevel getDifficultyLevel() {
        return difficulty;
    }

    /**
     * @return The track name
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     * Sets the instrument type for the track.
     * @param instrument The instrument type
     */
    public void setInstrument(final InstrumentType instrument) {
        this.instrument = instrument;
    }

    /**
     * @return The instrument type
     */
    public Enum<InstrumentType> getInstrument() {
        return instrument;
    }

    /**
     * Sets the track name.
     * @param trackName The track name
     */
    public void setTrackName(final String trackName) {
        this.trackName = trackName;
    }

    /**
     * @return The track channel
     */
    public int getChannel() {
        return trackID;
    }
}
