package studio.secretingredients.consult4me.controller.admin.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.User;

import java.util.List;

@Data
@AllArgsConstructor
public class UserGetResponse {

    private String result;

    private User user;

}
