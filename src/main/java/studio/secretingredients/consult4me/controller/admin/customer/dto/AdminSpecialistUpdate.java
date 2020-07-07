package studio.secretingredients.consult4me.controller.admin.customer.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistSpecialisation;

import java.util.Date;
import java.util.List;

@Data
public class AdminSpecialistUpdate extends BaseTokenRequest {

    private String specialistEmail;

    private Date birthDate;

    private String socialProfile;

    private String descriptionShort;

    private String descriptionDetailed;

    private String photo;

    private String video;

    private boolean active;

    private String education;

    private long priceHour;

    private String currency;

    private String pan;

    private String phone;

    private String firstName;

    private String lastName;

    private List<Long> specialisations;

    private String hashedPassword;

}
