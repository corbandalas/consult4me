package studio.secretingredients.consult4me.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studio.secretingredients.consult4me.domain.Property;


/**
 * Property repository interface
 *
 * @author corbandalas - created 12.05.2020
 * @since 0.1.0
 */

@Repository
public interface PropertyRepository extends CrudRepository<Property, Integer> {
    Property findPropertyById(Integer id);
    Property findPropertyByKey(String key);
}
