package studio.secretingredients.consult4me.controller.customer.register.dto;

import lombok.Data;
import studio.secretingredients.consult4me.domain.ChannelCategory;
import studio.secretingredients.consult4me.domain.SpecialisationCategory;
import studio.secretingredients.consult4me.domain.SpecialisationType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
public class SpecialistSpecialisation {

    private SpecialisationCategory specialisationCategory;
    private SpecialisationType specialisationType;

}