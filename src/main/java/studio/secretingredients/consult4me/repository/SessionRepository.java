package studio.secretingredients.consult4me.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.domain.Session;
import studio.secretingredients.consult4me.domain.SessionState;
import studio.secretingredients.consult4me.domain.Specialist;

import java.util.List;


/**
 * Session repository interface
 *
 * @author corbandalas - created 16.05.2020
 * @since 0.1.0
 */

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    List<Session> findByCustomer(Customer customer);
    List<Session> findBySpecialist(Specialist specialist);
    List<Session> findBySessionState(SessionState sessionState);
}
