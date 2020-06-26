package studio.secretingredients.consult4me.controller.frontend.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.Session;

@Data
@AllArgsConstructor
public class FrontendSpecialistInitSessionResponse {

    private String result;

//    private Session session;

    private String redirectHtml;

}
