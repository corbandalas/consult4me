package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Specialisation;
import studio.secretingredients.consult4me.domain.Specialist;
import studio.secretingredients.consult4me.repository.SpecialisationRepository;
import studio.secretingredients.consult4me.repository.SpecialistRepository;

/**
 * Specialist service
 *
 * @author corbandalas - created 17.05.2020
 * @since 0.1.0
 */
@Service("specialistService")
public class SpecialistService {

    @Autowired
    SpecialistRepository specialistRepository;

    public Specialist save(Specialist specialist) {
        return specialistRepository.save(specialist);
    }
}