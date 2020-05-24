package studio.secretingredients.consult4me.controller.frontend.register.dto;

import lombok.Data;
import studio.secretingredients.consult4me.domain.SpecialisationCategory;
import studio.secretingredients.consult4me.domain.SpecialisationType;

@Data
public class SpecialistSpecialisation {

    private SpecialisationCategory specialisationCategory;
    private SpecialisationType specialisationType;

}