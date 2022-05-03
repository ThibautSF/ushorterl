package thibaut.sf.ushorterl.services;

import thibaut.sf.ushorterl.kgs.exceptions.OutOfKeysException;
import thibaut.sf.ushorterl.models.Key;
import thibaut.sf.ushorterl.repositories.KeyRepository;

public interface KeyService {
    KeyRepository getRepo();

    Key saveKey(Key key);

    Key getUnusedKey() throws OutOfKeysException;
}
