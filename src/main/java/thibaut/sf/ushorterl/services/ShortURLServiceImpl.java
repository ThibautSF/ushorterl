package thibaut.sf.ushorterl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thibaut.sf.ushorterl.models.Key;
import thibaut.sf.ushorterl.models.ShortURL;
import thibaut.sf.ushorterl.repositories.ShortURLRepository;

@Service
public class ShortURLServiceImpl implements ShortURLService {
    @Autowired
    private ShortURLRepository shortURLRepository;

    @Override
    public ShortURLRepository getRepo() {
        return shortURLRepository;
    }

    @Override
    public ShortURL saveShortURL(ShortURL shortURL) {
        ShortURL response = shortURLRepository.save(shortURL);

        return response;
    }
    
    @Override
    public void deleteShortURL(ShortURL shortURL) {
        shortURLRepository.delete(shortURL);
    }

    @Override
    public ShortURL getShortURL(Key key) {
        return getShortURL(key.getKey());
    }

    @Override
    public ShortURL getShortURL(String keystr) {
        return shortURLRepository.findURLByShortURL(keystr);
    }
}
