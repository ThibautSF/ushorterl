package thibaut.sf.ushorterl.services;

import thibaut.sf.ushorterl.models.Key;
import thibaut.sf.ushorterl.models.ShortURL;
import thibaut.sf.ushorterl.repositories.ShortURLRepository;

public interface ShortURLService {
    ShortURLRepository getRepo();

    ShortURL saveShortURL(ShortURL shortURL);

    ShortURL getShortURL(Key key);

    ShortURL getShortURL(String keystr);
}
