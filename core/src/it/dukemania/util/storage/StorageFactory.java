package it.dukemania.util.storage;

public interface StorageFactory {
    /**
     * Creates a storage that points to the asset folder
     * @return Storage
     */
    Storage getAssetStorage();
    Storage getConfigurationStorage();
    Storage getExternalStorage(String path);
}
