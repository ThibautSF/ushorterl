package thibaut.sf.ushorterl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thibaut.sf.ushorterl.model.Key;
import thibaut.sf.ushorterl.repository.KeyRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service("KeyServiceImpl")
public class KeyServiceImpl implements KeyService {
    @Autowired
    private KeyRepository keyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Key saveKey(Key key) {
        Key response = keyRepository.save(key);

        return response;
    }

    @Override
    public Key getUnusedKey() {
        //TODO
        return null;
    }
}
