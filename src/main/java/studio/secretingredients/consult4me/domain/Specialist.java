package studio.secretingredients.consult4me.domain;

import lombok.Data;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Specialist entity
 *
 * @author corbandalas - created 15.05.2020
 * @since 0.1.0
 */
@Data
@Entity(name = "specialist")
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class Specialist extends Customer {

    @Column
    private Date birthDate;

    @Column
    private String socialProfile;

    @Column
    private String descriptionShort;

    @Column
    private String descriptionDetailed;

    @Column
    private String photo;

    @Column
    private String video;

    @Column
    private String education;

    @Column
    private long priceHour;

    @Column
    private String currency;

    @Column
    private String pan;

}

