package studio.secretingredients.consult4me.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.domain.User;

import java.util.Date;

@Data
@AllArgsConstructor
public class CustomerToken {

    private String token;
    private Customer customer;
    private Date authorizeDate;

}
