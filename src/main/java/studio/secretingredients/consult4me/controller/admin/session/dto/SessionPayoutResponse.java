package studio.secretingredients.consult4me.controller.admin.session.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.SessionPayout;

@Data
@AllArgsConstructor
public class SessionPayoutResponse {
    private String result;
    private SessionPayout sessionPayout;
}
