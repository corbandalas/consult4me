package studio.secretingredients.consult4me.controller.admin.customer.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

import java.util.Date;

@Data
public class AdminSpecialistUpdateTime extends BaseTokenRequest {

    private String specialistEmail;
    private long id;
    private Date startDate;
    private Date endDate;
    private boolean free;

}
