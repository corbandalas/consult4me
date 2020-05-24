package studio.secretingredients.consult4me.controller.frontend.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.Specialisation;

import java.util.List;

/**
 * Categories list DTO response object
 *
 * @author corbandalas - created 18.05.2020
 * @since 0.1.0
 */

@Data
@AllArgsConstructor
public class CategoriesListResponse {

    private String result;

    private List<Specialisation> specialisations;

}
