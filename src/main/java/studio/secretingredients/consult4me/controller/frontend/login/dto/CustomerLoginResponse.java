package studio.secretingredients.consult4me.controller.frontend.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Login DTO response object
 *
 * @author corbandalas - created 18.05.2020
 * @since 0.1.0
 */

@Data
@AllArgsConstructor
public class CustomerLoginResponse {

    private String result;
    private String token;

}
