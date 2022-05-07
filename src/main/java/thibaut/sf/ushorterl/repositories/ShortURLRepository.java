package thibaut.sf.ushorterl.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import thibaut.sf.ushorterl.models.ShortURL;

/**
 * API ShortURL repository
 *
 * @author ThibautSF
 * @version 1.0
 */
@Repository
public interface ShortURLRepository extends MongoRepository<ShortURL, String> {
    /**
     * Find a ShortURL by its identifier key
     *
     * @param shortURL a unique shortURL key
     * @return the ShortURL associated with this id (or null)
     */
    ShortURL findURLByShortURL(String shortURL);
}
