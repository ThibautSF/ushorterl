package thibaut.sf.ushorterl.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thibaut.sf.ushorterl.model.Key;

import java.util.List;

@Repository
public interface KeyRepository extends PagingAndSortingRepository<Key, String> {
    List<Key> findByUsed(@Param("used") boolean used);
}
