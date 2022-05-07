package thibaut.sf.ushorterl.services;

import thibaut.sf.ushorterl.exceptions.OutOfKeysException;
import thibaut.sf.ushorterl.models.Key;

/**
 * Key repository service interface
 *
 * @author ThibautSF
 * @version 1.0
 */
public interface KeyService {
    /**
     * Save a Key object
     *
     * @param key the key object to save
     * @return the key saved
     */
    Key saveKey(Key key);

    /**
     * Get an unused key
     *
     * @return an unused key
     * @throws OutOfKeysException if no more keys are available
     */
    Key getUnusedKey() throws OutOfKeysException;

    /**
     * @param Key the key object to get
     * @return the key found
     */
    Key getKey(String Key);
}
