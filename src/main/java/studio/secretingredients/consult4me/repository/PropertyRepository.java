package studio.secretingredients.consult4me.repository;

import com.bancore.paymentconsole.domain.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


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
