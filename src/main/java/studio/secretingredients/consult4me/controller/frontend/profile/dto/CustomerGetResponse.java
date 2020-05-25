package studio.secretingredients.consult4me.controller.frontend.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.Customer;

@Data
@AllArgsConstructor
public class CustomerGetResponse {

    private String result;
    private Customer customer;

}
