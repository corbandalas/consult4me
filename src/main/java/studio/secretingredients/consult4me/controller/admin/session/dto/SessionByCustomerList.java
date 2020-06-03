package studio.secretingredients.consult4me.controller.admin.session.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

@Data
public class SessionByCustomerList extends BaseTokenRequest {
    private String customerEmail;
}
