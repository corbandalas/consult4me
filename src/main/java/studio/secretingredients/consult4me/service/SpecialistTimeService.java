package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Specialist;
import studio.secretingredients.consult4me.domain.SpecialistTime;
import studio.secretingredients.consult4me.repository.SpecialistRepository;
import studio.secretingredients.consult4me.repository.SpecialistTimeRepository;

import java.util.Date;
import java.util.List;

/**
 * SpecialistTime service
 *
 * @author corbandalas - created 17.05.2020
 * @since 0.1.0
 */
@Service("specialistTimeService")
public class SpecialistTimeService {

    @Autowired
    SpecialistTimeRepository specialistTimeRepository;

    public SpecialistTime save(SpecialistTime specialistTime) {
        return specialistTimeRepository.save(specialistTime);
    }

    public List<SpecialistTime> findSpecialistTime(Specialist specialist) {
        return specialistTimeRepository.findBySpecialist(specialist);
    }

    public  List<SpecialistTime> findStartDateAfterStartAndEndDateBeforeEndBySpecialist(Date start, Date end, Specialist specialist) {
        return specialistTimeRepository.findStartDateAfterStartAndEndDateBeforeEndBySpecialist(start, end, specialist);
    }

    public SpecialistTime findById(long id) {
        return specialistTimeRepository.findById(id).get();
    }
}