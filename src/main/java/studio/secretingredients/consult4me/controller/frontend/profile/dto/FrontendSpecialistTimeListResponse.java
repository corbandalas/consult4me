package studio.secretingredients.consult4me.controller.frontend.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.SpecialistTime;

import java.util.List;

@Data
@AllArgsConstructor
public class FrontendSpecialistTimeListResponse {

    private String result;

    private List<SpecialistTime> specialistTime;

}
