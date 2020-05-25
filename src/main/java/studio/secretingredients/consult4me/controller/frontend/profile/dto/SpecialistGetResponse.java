package studio.secretingredients.consult4me.controller.frontend.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.domain.Specialist;

@Data
@AllArgsConstructor
public class SpecialistGetResponse {

    private String result;
    private Specialist specialist;

}
