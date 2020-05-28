package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Session;
import studio.secretingredients.consult4me.domain.Specialisation;
import studio.secretingredients.consult4me.domain.Specialist;
import studio.secretingredients.consult4me.repository.SessionRepository;
import studio.secretingredients.consult4me.repository.SpecialisationRepository;

import java.util.List;

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

    public List<Specialisation> findAll() {
        return (List<Specialisation>) specialisationRepository.findAll();
    }

    public Specialisation findById(long id) {
        return specialisationRepository.findById(id).get();
    }

//    public List<Specialisation> findBySpecialist(Specialist specialist) {
//        return specialisationRepository.findBySpecialist(specialist);
//    }

    public Specialisation save(Specialisation session) {
        return specialisationRepository.save(session);
    }
}