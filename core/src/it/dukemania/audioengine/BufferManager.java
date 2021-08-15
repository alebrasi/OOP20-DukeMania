package it.dukemania.audioengine;

import java.util.Iterator;

public interface BufferManager<T> extends Iterator<T> {
    /**
     * Restart the enveloper, first, 10 samples are used to take the volume from the current value to 0.
     * @param ttl the sustain time of the note
     */
    void refresh(long ttl);
}
