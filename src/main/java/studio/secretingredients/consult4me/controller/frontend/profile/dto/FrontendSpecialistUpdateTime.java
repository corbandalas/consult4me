package studio.secretingredients.consult4me.controller.frontend.profile.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

import java.util.Date;

@Data
public class FrontendSpecialistUpdateTime extends BaseTokenRequest {

    private long id;
    private Date startDate;
    private Date endDate;
    private boolean free;

}
