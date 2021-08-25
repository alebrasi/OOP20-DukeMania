package it.dukemania.util.storage;

public interface StorageFactory {
    /**
     * Creates a storage that points to the asset folder.
     * @return The associated asset storage
     */
    Storage getAssetStorage();

    /**
     * Creates a storage that points to the configuration folder storage.
     * @return The associated configuration storage.
     */
    Storage getConfigurationStorage();

    /**
     * Creates a storage that point to the same file path
     * @return The associated storage
     */
    Storage getExternalStorage();
}
