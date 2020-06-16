package studio.secretingredients.consult4me.controller.admin.session.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.SessionState;

@Data
public class SessionPayout extends BaseTokenRequest {
    private long sessionID;
}
