package it.dukemania.midi;

import java.util.List;

public class PercussionTrack extends ParsedTrack {


    /**
     * this is the constructor.
     * @param notes the list of notes that compose the track
     * @param channel the channel number associated to the track 
     */
    public PercussionTrack(final List<AbstractNote> notes, final int channel) {
        super(notes, channel);
    }


}
