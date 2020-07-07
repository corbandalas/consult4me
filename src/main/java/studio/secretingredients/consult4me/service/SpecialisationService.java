package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.*;
import studio.secretingredients.consult4me.repository.SessionRepository;
import studio.secretingredients.consult4me.repository.SpecialisationCategoryRepository;
import studio.secretingredients.consult4me.repository.SpecialisationRepository;
import studio.secretingredients.consult4me.repository.SpecialisationTypeRepository;

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

    @Autowired
    SpecialisationCategoryRepository specialisationCategoryRepository;

    @Autowired
    SpecialisationTypeRepository specialisationTypeRepository;

    public List<Specialisation> findAll() {
        return (List<Specialisation>) specialisationRepository.findAll();
    }

    public Specialisation findById(long id) {
        return specialisationRepository.findById(id).get();
    }

    public SpecialisationCategory addCategory(SpecialisationCategory specialisationCategory) {
        return specialisationCategoryRepository.save(specialisationCategory);
    }

    public SpecialisationType addType(SpecialisationType specialisationType) {
        return specialisationTypeRepository.save(specialisationType);
    }

    public List<SpecialisationCategory> findAllCategories() {
        return (List<SpecialisationCategory>) specialisationCategoryRepository.findAll();
    }

    public List<SpecialisationType> findAllTypes() {
        return (List<SpecialisationType>) specialisationTypeRepository.findAll();
    }

    public SpecialisationType findTypeById(long id) {
        return specialisationTypeRepository.findById(id).get();
    }

    public SpecialisationCategory findCategoryById(long id) {
        return specialisationCategoryRepository.findById(id).get();
    }

//    public List<Specialisation> findBySpecialist(Specialist specialist) {
//        return specialisationRepository.findBySpecialist(specialist);
//    }

    public Specialisation save(Specialisation specialisation) {
        return specialisationRepository.save(specialisation);
    }
}