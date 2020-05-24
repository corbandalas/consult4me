package studio.secretingredients.consult4me.controller.frontend.categories.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Categories list DTO request object
 *
 * @author corbandalas - created 20.05.2020
 * @since 0.1.0
 */

@Data
public class CategoriesList {

    @NotNull
    private String accountID;
    @NotNull
    private String checkSum;

}
