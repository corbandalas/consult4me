package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Session;
import studio.secretingredients.consult4me.domain.Specialisation;
import studio.secretingredients.consult4me.repository.SessionRepository;
import studio.secretingredients.consult4me.repository.SpecialisationRepository;

/**
 * Specialisation service
 *
 * @author corbandalas - created 17.05.2020
 * @since 0.1.0
 */
@Service("specialisationService")
public class SpecialisationService {

    @Autowired
    SpecialisationRepository specialisationRepository;

    public Specialisation save(Specialisation session) {
        return specialisationRepository.save(session);
    }
}