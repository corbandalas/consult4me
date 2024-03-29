package studio.secretingredients.consult4me.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Specialist time slot entity
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */
@Data
@Entity(name = "specialist_time")
public class SpecialistTime {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Specialist specialist;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private boolean free;


}

