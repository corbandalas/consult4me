package studio.secretingredients.consult4me.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import studio.secretingredients.consult4me.domain.Specialisation;
import studio.secretingredients.consult4me.domain.SpecialisationType;

/**
 * Specialisation type repository interface
 *
 * @author corbandalas - created 07.07.2020
 * @since 0.1.0
 */

@Repository
public interface SpecialisationTypeRepository extends CrudRepository<SpecialisationType, Long> {

}
