package studio.secretingredients.consult4me.controller.admin.user;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.authorization.admin.AdminUserAuthorized;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.admin.account.dto.AccountGetResponse;
import studio.secretingredients.consult4me.controller.admin.user.dto.*;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.AdminRole;
import studio.secretingredients.consult4me.domain.User;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.UserService;

import java.util.Date;

@RestController
@Slf4j
public class AdminUserController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @PostMapping(
            value = "/admin/getAllRoles", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_USER_GET_ALL_ROLES
    })
    public RolesGetResponse getRoles(@RequestBody RolesGet request) {

        return new RolesGetResponse(ResultCodes.OK_RESPONSE, AdminRole.values());
    }

    @PostMapping(
            value = "/admin/getUser", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_USER_GET
    })
    public UserGetResponse get(@RequestBody UserGet request) {

        User userByID = userService.findUserByID(request.getEmail());

        if (userByID == null) {
            return new UserGetResponse(ResultCodes.WRONG_USER, null);
        }

        return new UserGetResponse(ResultCodes.OK_RESPONSE, userByID);
    }


    @PostMapping(
            value = "/admin/listUsers", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_USER_LIST
    })
    public UserListResponse list(@RequestBody UserList request) {

        return new UserListResponse(ResultCodes.OK_RESPONSE, userService.findAll());
    }

    @PostMapping(
            value = "/admin/createUser", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_USER_CREATE
    })
    public UserCreateResponse create(@RequestBody UserCreate request) {

        if (request == null || StringUtils.isBlank(request.getEmail())
                || StringUtils.isBlank(request.getHashedPassword())
                || StringUtils.isBlank(request.getName())
        ) {
            return new UserCreateResponse(ResultCodes.WRONG_REQUEST, null);
        }

        User userByID = userService.findUserByID(request.getEmail());

        if (userByID != null) {
            return new UserCreateResponse(ResultCodes.WRONG_USER, null);
        }

        User user = new User();

        user.setActive(true);
        user.setEmail(request.getEmail());
        user.setHashedPassword(request.getHashedPassword());
        user.setName(request.getName());
        user.setRegisterDate(new Date());
        user.setRoles(request.getRoles());

        try {
            User save = userService.save(user);

            return new UserCreateResponse(ResultCodes.OK_RESPONSE, save);
        } catch (Exception e) {
            log.error("Account controller error", e);
        }

        return new UserCreateResponse(ResultCodes.GENERAL_ERROR, null);
    }

    @PostMapping(
            value = "/admin/updateUser", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_USER_UPDATE
    })
    public UserCreateResponse update(@RequestBody UserUpdate request) {

        if (request == null || StringUtils.isBlank(request.getEmail())
                || StringUtils.isBlank(request.getName())
        ) {
            return new UserCreateResponse(ResultCodes.WRONG_REQUEST, null);
        }

        User user = userService.findUserByID(request.getEmail());

        if (user == null) {
            return new UserCreateResponse(ResultCodes.WRONG_ACCOUNT, null);
        }

        user.setName(request.getName());

        if (!StringUtils.isBlank(request.getHashedPassword()))
            user.setHashedPassword(request.getHashedPassword());

        user.setActive(request.isActive());
        user.setRoles(request.getRoles());

        try {
            User save = userService.save(user);

            return new UserCreateResponse(ResultCodes.OK_RESPONSE, save);
        } catch (Exception e) {
            log.error("Account controller error", e);
        }

        return new UserCreateResponse(ResultCodes.GENERAL_ERROR, null);
    }

    @PostMapping(
            value = "/admin/addUserToAccount", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_USER_ADD_TO_ACCOUNT
    })
    public AccountGetResponse addUser(@RequestBody UserAddToAccount request) {

        try {

            User userByID = userService.findUserByID(request.getEmail());

            Account accountByID = accountService.findAccountByID(request.getId());

            if (userByID == null) {
                return new AccountGetResponse(ResultCodes.WRONG_USER, null);
            }

            if (accountByID == null) {
                return new AccountGetResponse(ResultCodes.WRONG_ACCOUNT, null);
            }

            if (!accountByID.getUsers().contains(userByID)) {
                accountByID.getUsers().add(userByID);
                Account save = accountService.save(accountByID);

                return new AccountGetResponse(ResultCodes.OK_RESPONSE, save);
            }

        } catch (Exception e) {
            log.error("UserController", e);
        }
        return new AccountGetResponse(ResultCodes.GENERAL_ERROR, null);
    }


    @PostMapping(
            value = "/admin/removeUserFromAccount", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_USER_ADD_TO_ACCOUNT
    })
    public AccountGetResponse removeUser(@RequestBody UserAddToAccount request) {

        try {

            User userByID = userService.findUserByID(request.getEmail());

            Account accountByID = accountService.findAccountByID(request.getId());

            if (userByID == null) {
                return new AccountGetResponse(ResultCodes.WRONG_USER, null);
            }

            if (accountByID == null) {
                return new AccountGetResponse(ResultCodes.WRONG_ACCOUNT, null);
            }

            if (accountByID.getUsers().contains(userByID)) {
                accountByID.getUsers().remove(userByID);
                Account save = accountService.save(accountByID);

                return new AccountGetResponse(ResultCodes.OK_RESPONSE, save);
            }

        } catch (Exception e) {
            log.error("UserController", e);
        }
        return new AccountGetResponse(ResultCodes.GENERAL_ERROR, null);
    }

}



