package studio.secretingredients.consult4me.controller.admin.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.Property;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountListResponse {

    private String result;

    private List<Account> accounts;

}
