package thibaut.sf.ushorterl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thibaut.sf.ushorterl.models.Key;
import thibaut.sf.ushorterl.models.ShortURL;
import thibaut.sf.ushorterl.repositories.ShortURLRepository;

/**
 * ShortURL repository service implementation
 *
 * @author ThibautSF
 * @version 1.0
 */
@Service
public class ShortURLServiceImpl implements ShortURLService {
    /**
     * The ShortURL repository serviced
     */
    @Autowired
    private ShortURLRepository shortURLRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortURL saveShortURL(ShortURL shortURL) {
        ShortURL response = shortURLRepository.save(shortURL);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteShortURL(ShortURL shortURL) {
        shortURLRepository.delete(shortURL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortURL getShortURL(Key key) {
        return getShortURL(key.getKey());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortURL getShortURL(String keystr) {
        return shortURLRepository.findURLByShortURL(keystr);
    }
}
