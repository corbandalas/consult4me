package studio.secretingredients.consult4me.controller.admin.property.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.Property;

import java.util.List;

@Data
@AllArgsConstructor
public class PropertyUpdateResponse {

    private String result;

    private Property property;

}
