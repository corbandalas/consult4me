package studio.secretingredients.consult4me.controller.admin.user.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.AdminRole;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserUpdate extends BaseTokenRequest {

    private String email;
    private String hashedPassword;
    private String name;
    private boolean active;
    private Set<AdminRole> roles = new HashSet<>();

}
