package studio.secretingredients.consult4me.controller.admin.session.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.SessionPayout;

import java.util.List;

@Data
@AllArgsConstructor
public class SessionPayoutListResponse {
    private String result;
    private List<SessionPayout> sessionPayouts;
}
