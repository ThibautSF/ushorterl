package thibaut.sf.ushorterl.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import thibaut.sf.ushorterl.models.Key;

import java.util.List;

/**
 * KGS key repository
 *
 * @author ThibautSF
 * @version 1.0
 */
@Repository
public interface KeyRepository extends PagingAndSortingRepository<Key, String> {
    /**
     * @param key a unique string key id
     * @return the key associated with this id (or null)
     */
    Key findByKey(String key);

    /**
     * @param used     key usage filter
     * @param pageable page and page size
     * @return a list of keys
     */
    List<Key> findByUsed(boolean used, Pageable pageable);
}
