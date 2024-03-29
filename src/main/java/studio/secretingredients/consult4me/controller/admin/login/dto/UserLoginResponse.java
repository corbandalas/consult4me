package studio.secretingredients.consult4me.controller.admin.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.User;

/**
 * Login DTO response object
 *
 * @author corbandalas - created 18.05.2020
 * @since 0.1.0
 */

@Data
@AllArgsConstructor
public class UserLoginResponse {

    private String result;
    private String token;
    private User user;

}
