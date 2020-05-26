package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Specialisation;
import studio.secretingredients.consult4me.domain.Specialist;
import studio.secretingredients.consult4me.repository.SpecialistRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Specialist> findSpecialistByEmail(String email) {
        return specialistRepository.findById(email);
    }

    public List<Specialist> findAll() {
        return (List<Specialist>) specialistRepository.findAll();
    }

    public Specialist save(Specialist specialist) {
        return specialistRepository.save(specialist);
    }
}