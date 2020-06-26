package studio.secretingredients.consult4me.controller.frontend.profile.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class FrontendFindTime extends BaseTokenRequest {

    @NotNull
    private String accountID;
    @NotNull
    private String checkSum;

    private String specialistEmail;

    private Date startSearchPeriod;

    private Date endSearchPeriod;

}
