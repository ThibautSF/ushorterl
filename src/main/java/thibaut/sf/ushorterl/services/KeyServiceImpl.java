package thibaut.sf.ushorterl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import thibaut.sf.ushorterl.exceptions.OutOfKeysException;
import thibaut.sf.ushorterl.models.Key;
import thibaut.sf.ushorterl.repositories.KeyRepository;

import java.security.SecureRandom;
import java.util.List;

/**
 * Key repository service implementation
 *
 * @author ThibautSF
 * @version 1.0
 */
@Service
public class KeyServiceImpl implements KeyService {
    /**
     * The key repository serviced
     */
    @Autowired
    private KeyRepository keyRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Key saveKey(Key key) {
        return keyRepository.save(key);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get a random key from the 1000 first unused keys
     * </p>
     */
    @Override
    public Key getUnusedKey() throws OutOfKeysException {
        List<Key> keys = keyRepository.findByUsed(false, PageRequest.ofSize(1000));

        SecureRandom rand = new SecureRandom();

        if (keys.size() == 0) {
            throw new OutOfKeysException();
        }

        return keys.get(rand.nextInt(keys.size()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Key getKey(String keystr) {
        return keyRepository.findByKey(keystr);
    }
}
