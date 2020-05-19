package studio.secretingredients.consult4me.controller.customer.register.dto;

import lombok.Data;
import studio.secretingredients.consult4me.domain.Specialisation;

import java.util.Date;
import java.util.List;


/**
 * Register DTO request object
 *
 * @author corbandalas - created 19.05.2020
 * @since 0.1.0
 */

@Data
public class SpecialistRegister {

    private String email;
    private String hashedPassword;
    private String phone;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String socialProfile;
    private String descriptionShort;
    private String descriptionDetailed;
    private String photo;
    private String video;
    private String education;
    private long priceHour;
    private String currency;
    private String pan;
    private List<SpecialistSpecialisation> specialisations;

    private String accountID;
    private String checkSum;

}
