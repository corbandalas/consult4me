package studio.secretingredients.consult4me.controller.admin.customer.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistSpecialisation;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


/**
 * Register DTO request object for specialist in admin console
 *
 * @author corbandalas - created 27.05.2020
 * @since 0.1.0
 */

@Data
public class AdminSpecialistRegister extends BaseTokenRequest{

    @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private Date birthDate;
    @NotNull
    private String socialProfile;
    @NotNull
    private String descriptionShort;
    @NotNull
    private String descriptionDetailed;
    @NotNull
    private String photo;
    @NotNull
    private String video;
    @NotNull
    private String education;
    @NotNull
    private long priceHour;
    @NotNull
    private String currency;
    @NotNull
    private String pan;
    @NotNull
    private boolean active;
    @NotNull
    private List<Long> specialisations;

}
