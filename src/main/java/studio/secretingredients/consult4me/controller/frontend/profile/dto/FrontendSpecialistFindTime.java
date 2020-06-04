package studio.secretingredients.consult4me.controller.frontend.profile.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

import java.util.Date;

@Data
public class FrontendSpecialistFindTime extends BaseTokenRequest {

    private Date startSearchPeriod;

    private Date endSearchPeriod;

}
