package studio.secretingredients.consult4me.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Specialisation category types
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */

@Data
@Entity(name = "specialisation_type")
public class SpecialisationType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;
}