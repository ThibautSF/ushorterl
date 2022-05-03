package thibaut.sf.ushorterl.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import thibaut.sf.ushorterl.models.ShortURL;

@Repository
public interface ShortURLRepository extends MongoRepository<ShortURL, String> {
    ShortURL findURLByShortURL(String shortURL);
}
