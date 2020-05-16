package studio.secretingredients.consult4me.domain;

import lombok.Data;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private SpecialisationCategory specialisationCategory;

    @Enumerated(EnumType.STRING)
    private SpecialisationType specialisationType;

}

