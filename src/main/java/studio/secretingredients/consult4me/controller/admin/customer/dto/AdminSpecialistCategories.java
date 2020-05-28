package studio.secretingredients.consult4me.controller.admin.customer.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistSpecialisation;

import java.util.Date;
import java.util.List;

@Data
public class AdminSpecialistCategories extends BaseTokenRequest {

    private String specialistEmail;

}
