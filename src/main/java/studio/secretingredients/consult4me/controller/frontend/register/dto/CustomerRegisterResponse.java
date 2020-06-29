package studio.secretingredients.consult4me.controller.frontend.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Register DTO response object
 *
 * @author corbandalas - created 19.05.2020
 * @since 0.1.0
 */

@Data
@AllArgsConstructor
public class CustomerRegisterResponse {

    private String result;

    private String token;

}
