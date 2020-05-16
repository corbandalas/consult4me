package studio.secretingredients.consult4me.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.domain.Session;


/**
 * Session repository interface
 *
 * @author corbandalas - created 16.05.2020
 * @since 0.1.0
 */

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
}
