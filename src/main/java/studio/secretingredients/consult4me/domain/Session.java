package studio.secretingredients.consult4me.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Specialist session  entity
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */
@Data
@Entity(name = "session")
public class Session {

    @Id
    private long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Specialist specialist;

    @Column
    private Date orderedDate;

    @Enumerated(EnumType.STRING)
    private SessionState sessionState;

    @OneToOne
    private SpecialistTime specialistTime;

    @Column
    private long price;

    @Column
    private long totalPrice;

    @Column
    private long fee;

    @Column
    private String currency;

}

