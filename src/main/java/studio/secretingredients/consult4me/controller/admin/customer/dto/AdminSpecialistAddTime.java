package studio.secretingredients.consult4me.controller.admin.customer.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

import java.util.Date;

@Data
public class AdminSpecialistAddTime extends BaseTokenRequest {

    private String specialistEmail;
    private Date startDate;
    private Date endDate;

}
