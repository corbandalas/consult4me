package studio.secretingredients.consult4me.controller.frontend.profile.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.Specialisation;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Data
public class SpecialistUpdate extends BaseTokenRequest {

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

    private String phone;

    private String firstName;

    private String lastName;

    private List<Specialisation> specialisations;

    private String hashedPassword;

}
