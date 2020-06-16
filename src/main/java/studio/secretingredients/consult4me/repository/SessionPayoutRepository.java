package studio.secretingredients.consult4me.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studio.secretingredients.consult4me.domain.*;

import java.util.Date;
import java.util.List;


/**
 * Session payout repository interface
 *
 * @author corbandalas - created 16.05.2020
 * @since 0.1.0
 */

@Repository
public interface SessionPayoutRepository extends CrudRepository<SessionPayout, Long> {
    SessionPayout findBySession(Session session);
    List<SessionPayout> findBySessionSpecialist(Specialist specialist);
    List<SessionPayout> findByDateBetween(Date startDate, Date endDate);
    List<SessionPayout> findBySessionSpecialistAndDateBetween(Specialist specialist, Date startDate, Date endDate);
}
