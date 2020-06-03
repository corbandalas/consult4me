package studio.secretingredients.consult4me.controller.admin.session.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.SessionState;

@Data
public class SessionByStateList extends BaseTokenRequest {
    private SessionState sessionState;
}
