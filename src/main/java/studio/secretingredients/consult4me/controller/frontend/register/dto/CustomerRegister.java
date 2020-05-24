package studio.secretingredients.consult4me.controller.frontend.register.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Register DTO request object
 *
 * @author corbandalas - created 19.05.2020
 * @since 0.1.0
 */

@Data
public class CustomerRegister {

    @NotNull
    private String email;
    @NotNull
    private String hashedPassword;
    @NotNull
    private String phone;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String accountID;
    @NotNull
    private String checkSum;

    private List<CustomerChannel> channels;

}
