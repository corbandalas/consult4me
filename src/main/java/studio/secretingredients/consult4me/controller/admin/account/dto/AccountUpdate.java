package studio.secretingredients.consult4me.controller.admin.account.dto;

import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;

import java.util.Date;

@Data
public class AccountUpdate extends BaseTokenRequest {

    private Integer id;
    private String contactPersonName;
    private String phoneNumber;
    private boolean active;

}
