package studio.secretingredients.consult4me.controller.customer.register.dto;

import lombok.Data;
import studio.secretingredients.consult4me.domain.Channel;

import java.util.List;


/**
 * Register DTO request object
 *
 * @author corbandalas - created 19.05.2020
 * @since 0.1.0
 */

@Data
public class CustomerRegister {

    private String email;
    private String hashedPassword;
    private String phone;
    private String firstName;
    private String lastName;
    private String accountID;
    private String checkSum;

    private List<CustomerChannel> channels;

}
