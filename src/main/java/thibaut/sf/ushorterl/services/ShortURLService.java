package thibaut.sf.ushorterl.services;

import thibaut.sf.ushorterl.models.Key;
import thibaut.sf.ushorterl.models.ShortURL;

/**
 * ShortURL repository service interface
 *
 * @author ThibautSF
 * @version 1.0
 */
public interface ShortURLService {
    /**
     * Save a ShortURL object
     *
     * @param shortURL the ShortURL object to save
     * @return the ShortURL object saved
     */
    ShortURL saveShortURL(ShortURL shortURL);

    /**
     * Delete a ShortURL object
     *
     * @param shortURL the ShortURL object to delete
     */
    void deleteShortURL(ShortURL shortURL);

    /**
     * Get a ShortURL object based on Key
     *
     * @param key Key object ({@link Key#getKey()})
     * @return the ShortURL found
     */
    ShortURL getShortURL(Key key);

    /**
     * @param keystr the shortURL key identifier
     * @return the ShortURL found
     */
    ShortURL getShortURL(String keystr);
}
