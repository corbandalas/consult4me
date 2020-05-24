package studio.secretingredients.consult4me.controller.frontend.login.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Login DTO request object
 *
 * @author corbandalas - created 18.05.2020
 * @since 0.1.0
 */

@Data
public class SpecialistLogin {

    @NotNull
    private String accountID;
    @NotNull
    private String login;
    @NotNull
    private String hashedPassword;
    @NotNull
    private String checkSum;

}
