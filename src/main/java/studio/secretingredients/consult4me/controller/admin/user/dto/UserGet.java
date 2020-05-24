package studio.secretingredients.consult4me.controller.admin.user.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.AdminRole;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserGet extends BaseTokenRequest {

    private String email;

}
