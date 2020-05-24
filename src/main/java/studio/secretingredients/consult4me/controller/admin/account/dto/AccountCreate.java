package studio.secretingredients.consult4me.controller.admin.account.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class AccountCreate extends BaseTokenRequest {

    private String contactPersonName;
    private String phoneNumber;

}
