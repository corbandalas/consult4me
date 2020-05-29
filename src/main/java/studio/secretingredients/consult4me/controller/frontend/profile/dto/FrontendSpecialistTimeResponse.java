package studio.secretingredients.consult4me.controller.frontend.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.SpecialistTime;

@Data
@AllArgsConstructor
public class FrontendSpecialistTimeResponse {

    private String result;

    private SpecialistTime specialistTime;
}
