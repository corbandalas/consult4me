package studio.secretingredients.consult4me.authorization.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.User;

import java.util.Date;

@Data
@AllArgsConstructor
public class AdminUserToken {

    private String token;
    private User user;
    private Date authorizeDate;

}
