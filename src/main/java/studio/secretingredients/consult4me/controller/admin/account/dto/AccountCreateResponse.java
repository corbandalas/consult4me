package studio.secretingredients.consult4me.controller.admin.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreateResponse {

    private String result;
    private studio.secretingredients.consult4me.domain.Account account;

}
