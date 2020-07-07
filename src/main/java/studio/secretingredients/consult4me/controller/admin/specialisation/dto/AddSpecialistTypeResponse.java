package studio.secretingredients.consult4me.controller.admin.specialisation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.SpecialisationType;
import studio.secretingredients.consult4me.domain.User;

/**
 * Specialisation type DTO response object
 *
 * @author corbandalas - created 07.07.2020
 * @since 0.1.0
 */

@Data
@AllArgsConstructor
public class AddSpecialistTypeResponse {

    private String result;
    private SpecialisationType specialisationType;

}
