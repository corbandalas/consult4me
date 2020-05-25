package studio.secretingredients.consult4me.controller.admin.user.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

@Data
public class UserAddToAccount extends BaseTokenRequest {

    private String email;
    private Integer id;

}
