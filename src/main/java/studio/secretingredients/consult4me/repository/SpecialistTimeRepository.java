package studio.secretingredients.consult4me.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studio.secretingredients.consult4me.domain.Specialist;
import studio.secretingredients.consult4me.domain.SpecialistTime;

import java.util.Date;
import java.util.List;


/**
 * Specialist time repository interface
 *
 * @author corbandalas - created 16.05.2020
 * @since 0.1.0
 */

@Repository
public interface SpecialistTimeRepository extends CrudRepository<SpecialistTime, Long> {
    public List<SpecialistTime> findBySpecialist(Specialist specialist);
}
