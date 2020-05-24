package studio.secretingredients.consult4me.controller.admin.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.User;

@Data
@AllArgsConstructor
public class AccountGetResponse {

    private String result;

    private Account account;

}
