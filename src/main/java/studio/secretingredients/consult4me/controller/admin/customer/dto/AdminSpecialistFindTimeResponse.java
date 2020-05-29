package studio.secretingredients.consult4me.controller.admin.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.SpecialistTime;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminSpecialistFindTimeResponse extends BaseTokenRequest {

    private String result;

    private List<SpecialistTime> specialistTime;
}
