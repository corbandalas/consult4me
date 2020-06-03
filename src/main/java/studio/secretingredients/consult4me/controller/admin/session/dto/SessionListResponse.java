package studio.secretingredients.consult4me.controller.admin.session.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.Session;

import java.util.List;

@Data
@AllArgsConstructor
public class SessionListResponse {

    private String result;

    public List<Session> sessions;
}
