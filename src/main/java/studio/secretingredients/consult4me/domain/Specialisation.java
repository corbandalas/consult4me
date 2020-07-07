package studio.secretingredients.consult4me.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Specialisation entity
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */
@Data
@Entity(name = "specialisation")
public class Specialisation {

    @Id
    private long id;

    @ManyToOne
    private SpecialisationCategory specialisationCategory;

    @ManyToOne
    private SpecialisationType specialisationType;

    @ManyToMany(
            mappedBy = "specialisations")
    private List<Specialist> specialists;

}

