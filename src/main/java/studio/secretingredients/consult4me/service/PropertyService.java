package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Property;
import studio.secretingredients.consult4me.repository.PropertyRepository;

import java.util.List;

/**
 * Property service
 *
 * @author corbandalas - created 14.05.2020
 * @since 0.1.0
 */
@Service("propertyService")
public class PropertyService {
    @Autowired
    PropertyRepository propertyRepository;

    public List<Property> findAll() {
        return (List<Property>) propertyRepository.findAll();
    }

    public Property findPropertyByKey(String key) {
        return propertyRepository.findPropertyByKey(key);
    }
    public Property findPropertyById(Integer id) {
        return propertyRepository.findPropertyById(id);
    }

    public Property save(Property property) {
        return propertyRepository.save(property);
    }
}