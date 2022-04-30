package thibaut.sf.ushorterl.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import thibaut.sf.ushorterl.model.ShortURL;

public interface ShortURLRepository extends MongoRepository<ShortURL, String> {
    ShortURL findURLById(String shortURL);
}
