package studio.secretingredients.consult4me.controller.admin.session.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

@Data
public class SessionBySpecialistList extends BaseTokenRequest {
    private String specialistEmail;
}
