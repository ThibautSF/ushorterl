package thibaut.sf.ushorterl.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import thibaut.sf.ushorterl.models.Key;

import java.util.List;

@Repository
public interface KeyRepository extends PagingAndSortingRepository<Key, String> {
    List<Key> findByUsed(boolean used, Pageable pageable);
}
