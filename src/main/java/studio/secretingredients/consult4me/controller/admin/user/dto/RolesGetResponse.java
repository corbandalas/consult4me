package studio.secretingredients.consult4me.controller.admin.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.AdminRole;
import studio.secretingredients.consult4me.domain.User;

import java.util.List;

@Data
@AllArgsConstructor
public class RolesGetResponse {

    private String result;

    private AdminRole[] roles;

}
