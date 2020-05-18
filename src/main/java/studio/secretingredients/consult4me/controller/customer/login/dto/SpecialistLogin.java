package studio.secretingredients.consult4me.controller.customer.login.dto;

import lombok.Data;

/**
 * Login DTO request object
 *
 * @author corbandalas - created 18.05.2020
 * @since 0.1.0
 */

@Data
public class SpecialistLogin {

    private String accountID;
    private String login;
    private String hashedPassword;
    private String checkSum;

}
