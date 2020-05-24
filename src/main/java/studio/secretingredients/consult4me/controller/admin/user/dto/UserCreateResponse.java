package studio.secretingredients.consult4me.controller.admin.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateResponse {

    private String result;
    private studio.secretingredients.consult4me.domain.User user;

}
