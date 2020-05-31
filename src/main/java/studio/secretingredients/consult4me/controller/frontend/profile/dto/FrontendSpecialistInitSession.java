package studio.secretingredients.consult4me.controller.frontend.profile.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.domain.SessionState;
import studio.secretingredients.consult4me.domain.Specialist;
import studio.secretingredients.consult4me.domain.SpecialistTime;

import javax.persistence.*;
import java.util.Date;

@Data
public class FrontendSpecialistInitSession extends BaseTokenRequest {

    private String specialistEmail;

    private long specialistTimeID;

}
