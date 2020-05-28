package studio.secretingredients.consult4me.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studio.secretingredients.consult4me.domain.Session;
import studio.secretingredients.consult4me.domain.Specialisation;
import studio.secretingredients.consult4me.domain.Specialist;

import java.util.List;


/**
 * Specialisation repository interface
 *
 * @author corbandalas - created 16.05.2020
 * @since 0.1.0
 */

@Repository
public interface SpecialisationRepository extends CrudRepository<Specialisation, Long> {

    List<Specialisation> findBySpecialist(Specialist specialist);
}
