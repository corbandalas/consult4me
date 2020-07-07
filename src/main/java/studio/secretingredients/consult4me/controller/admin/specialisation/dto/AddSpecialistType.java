package studio.secretingredients.consult4me.controller.admin.specialisation.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

@Data
public class AddSpecialistType extends BaseTokenRequest {
    private String name;
    private String description;
}
