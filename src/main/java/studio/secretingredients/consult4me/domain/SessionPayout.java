package studio.secretingredients.consult4me.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Specialist session payout entity
 *
 * @author corbandalas - created 16.05.2020
 * @since 0.1.0
 */
@Data
@Entity(name = "session_payout")
public class SessionPayout {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Session session;

    @Column
    private Date date;

    @Column
    private long payoutAmount;

    @Column
    private String payoutCurrency;

    @Column
    private String orderID;

}

