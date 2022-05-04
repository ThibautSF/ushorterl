package thibaut.sf.ushorterl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import thibaut.sf.ushorterl.kgs.exceptions.OutOfKeysException;
import thibaut.sf.ushorterl.models.Key;
import thibaut.sf.ushorterl.repositories.KeyRepository;

import java.security.SecureRandom;
import java.util.List;

@Service
public class KeyServiceImpl implements KeyService {
    @Autowired
    private KeyRepository keyRepository;

    @Override
    public KeyRepository getRepo() {
        return keyRepository;
    }

    @Override
    public Key saveKey(Key key) {
        Key response = keyRepository.save(key);

        return response;
    }

    @Override
    public Key getUnusedKey() throws OutOfKeysException {
        List<Key> keys = keyRepository.findByUsed(false, PageRequest.ofSize(1000));

        SecureRandom rand = new SecureRandom();

        if (keys.size() == 0) {
            throw new OutOfKeysException();
        }

        return keys.get(rand.nextInt(keys.size()));
    }

    @Override
    public Key getKey(String keystr) {
        return keyRepository.findByKey(keystr);
    }
}
