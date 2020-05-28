package studio.secretingredients.consult4me.controller.admin.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.Specialisation;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class AdminSpecialistCategoriesResponse {

    private String result;

    private Set<Specialisation> specialisationList;


}
