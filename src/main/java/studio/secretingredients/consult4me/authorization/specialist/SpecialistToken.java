package studio.secretingredients.consult4me.authorization.specialist;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.domain.Specialist;

import java.util.Date;

@Data
@AllArgsConstructor
public class SpecialistToken {

    private String token;
    private Specialist specialist;
    private Date authorizeDate;
    private Account account;

}
