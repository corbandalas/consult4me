package studio.secretingredients.consult4me.controller.admin.account;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.authorization.admin.AdminUserAuthorized;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.admin.account.dto.*;
import studio.secretingredients.consult4me.controller.admin.user.dto.UserGet;
import studio.secretingredients.consult4me.controller.admin.user.dto.UserGetResponse;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.AdminRole;
import studio.secretingredients.consult4me.domain.User;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.Date;

@RestController
@Slf4j
public class AdminAccountController {

    @Autowired
    AccountService accountService;


    @PostMapping(
            value = "/admin/getAccount", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_ACCOUNT_GET
    })
    public AccountGetResponse get(@RequestBody AccountGet request) {

        Account account = accountService.findAccountByID(request.getId());

        if (account == null) {
            return new AccountGetResponse(ResultCodes.WRONG_USER, null);
        }

        return new AccountGetResponse(ResultCodes.OK_RESPONSE, account);
    }

    @PostMapping(
            value = "/admin/listAccounts", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_ACCOUNT_LIST
    })
    public AccountListResponse list(@RequestBody AccountList request) {

        return new AccountListResponse(ResultCodes.OK_RESPONSE, accountService.findAll());
    }

    @PostMapping(
            value = "/admin/createAccount", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_ACCOUNT_CREATE
    })
    public AccountCreateResponse create(@RequestBody AccountCreate request) {

        if (request == null || StringUtils.isBlank(request.getContactPersonName())
                || StringUtils.isBlank(request.getPhoneNumber())
        ) {
            return new AccountCreateResponse(ResultCodes.WRONG_REQUEST, null);
        }

        Account account = new Account();

        account.setActive(true);
        account.setContactPersonName(request.getContactPersonName());
        account.setPhoneNumber(request.getPhoneNumber());
        account.setPrivateKey(SecurityUtil.generateKeyFromArray(request.getContactPersonName(), request.getPhoneNumber()));
        account.setRegisterDate(new Date());

        try {
            Account save = accountService.save(account);

            return new AccountCreateResponse(ResultCodes.OK_RESPONSE, save);
        } catch (Exception e) {
            log.error("Account controller error", e);
        }

        return new AccountCreateResponse(ResultCodes.GENERAL_ERROR, null);
    }

    @PostMapping(
            value = "/admin/updateAccount", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_ACCOUNT_UPDATE
    })
    public AccountCreateResponse update(@RequestBody AccountUpdate request) {

        if (request == null || StringUtils.isBlank(request.getContactPersonName())
                || StringUtils.isBlank(request.getPhoneNumber())
                || StringUtils.isBlank(request.getPhoneNumber())
                || request.getId() == null
        ) {
            return new AccountCreateResponse(ResultCodes.WRONG_REQUEST, null);
        }

        Account accountByID = accountService.findAccountByID(request.getId());

        if (accountByID == null) {
            return new AccountCreateResponse(ResultCodes.WRONG_ACCOUNT, null);
        }

        accountByID.setPhoneNumber(request.getPhoneNumber());
        accountByID.setContactPersonName(request.getContactPersonName());
        accountByID.setActive(request.isActive());

        try {
            Account save = accountService.save(accountByID);

            return new AccountCreateResponse(ResultCodes.OK_RESPONSE, save);
        } catch (Exception e) {
            log.error("Account controller error", e);
        }

        return new AccountCreateResponse(ResultCodes.GENERAL_ERROR, null);
    }

}



