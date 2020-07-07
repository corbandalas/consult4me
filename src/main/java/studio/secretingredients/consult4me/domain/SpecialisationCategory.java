package studio.secretingredients.consult4me.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Specialisation category enumeration
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */

@Data
@Entity(name = "specialisation_category")
public class SpecialisationCategory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

}