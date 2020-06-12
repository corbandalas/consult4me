package studio.secretingredients.consult4me.controller.admin.property.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

@Data
public class PropertyUpdate extends BaseTokenRequest {
    private int id;
    private String value;
}
