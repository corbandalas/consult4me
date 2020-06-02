package studio.secretingredients.consult4me.controller.admin.customer.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

import java.util.Date;

@Data
public class AdminSpecialistFindTime extends BaseTokenRequest {

    private String specialistEmail;

    private Date startSearchPeriod;

    private Date endSearchPeriod;

}
