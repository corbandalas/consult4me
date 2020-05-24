package studio.secretingredients.consult4me.controller.admin.property.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.Property;
import studio.secretingredients.consult4me.domain.Specialisation;

import java.util.List;

@Data
@AllArgsConstructor
public class PropertyListResponse {

    private String result;

    private List<Property> properties;

}
