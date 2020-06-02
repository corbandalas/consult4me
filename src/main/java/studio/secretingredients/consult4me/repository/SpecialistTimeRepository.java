package studio.secretingredients.consult4me.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
    List<SpecialistTime> findBySpecialist(Specialist specialist);

    @Query("select a from specialist_time a where a.startDate >= :startPeriod and a.endDate <= endPeriod and a.specialist.email = specialist.email")
    List<SpecialistTime> findAllByStartDateAfterStartAndEndDateBeforeEndBySpecialist(@Param("startPeriod") Date startPeriod,@Param("endPeriod") Date endPeriod, @Param("specialist")Specialist specialist);
}
