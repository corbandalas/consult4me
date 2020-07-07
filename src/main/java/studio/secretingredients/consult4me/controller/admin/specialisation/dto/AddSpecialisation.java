package studio.secretingredients.consult4me.controller.admin.specialisation.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

@Data
public class AddSpecialisation extends BaseTokenRequest {

    private Long categoryID;
    private Long typeID;

}
