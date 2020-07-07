package studio.secretingredients.consult4me.controller.admin.specialisation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.Specialisation;
import studio.secretingredients.consult4me.domain.SpecialisationType;

/**
 * Specialisation type DTO response object
 *
 * @author corbandalas - created 07.07.2020
 * @since 0.1.0
 */

@Data
@AllArgsConstructor
public class AddSpecialisationResponse {

    private String result;
    private Specialisation specialisation;

}
