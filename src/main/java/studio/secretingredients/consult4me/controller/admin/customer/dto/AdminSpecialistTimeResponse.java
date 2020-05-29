package studio.secretingredients.consult4me.controller.admin.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.SpecialistTime;

@Data
@AllArgsConstructor
public class AdminSpecialistTimeResponse {

    private String result;

    private SpecialistTime specialistTime;
}
