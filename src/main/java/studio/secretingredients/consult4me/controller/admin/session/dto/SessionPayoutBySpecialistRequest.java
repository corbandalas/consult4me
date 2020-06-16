package studio.secretingredients.consult4me.controller.admin.session.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

import java.util.Date;

@Data
public class SessionPayoutBySpecialistRequest extends BaseTokenRequest {
    private String specialistEmail;
    private Date startDate;
    private Date endDate;
}
