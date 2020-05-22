package studio.secretingredients.consult4me.controller.admin.login;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.CacheProvider;
import studio.secretingredients.consult4me.authorization.admin.AdminUserToken;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.admin.login.dto.UserLogin;
import studio.secretingredients.consult4me.controller.admin.login.dto.UserLoginResponse;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.User;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.UserService;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.Date;

@Slf4j
@RestController
public class AdminAccountLoginController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    CacheProvider cacheProvider;

    @PostMapping(
            value = "/admin/login", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "SHA256(accountID+login+phone+hashedPassword+privateKey)"
                    , name = "checksum")})
    public UserLoginResponse login(@RequestBody UserLogin userLogin) {

        try {
            if (userLogin == null || StringUtils.isBlank(userLogin.getAccountID())
                    || StringUtils.isBlank(userLogin.getHashedPassword())
                    || StringUtils.isBlank(userLogin.getCheckSum())
                    || StringUtils.isBlank(userLogin.getLogin())
            ) {
                return new UserLoginResponse(ResultCodes.WRONG_REQUEST, null);
            }

            Account account = accountService.findAccountByID(Integer.parseInt(userLogin.getAccountID()));

            if (account == null || !account.isActive()) {
                return new UserLoginResponse(ResultCodes.WRONG_ACCOUNT, null);
            }

            User user = userService.findUserByID(userLogin.getLogin());

            if (user == null || !user.isActive()) {
                return new UserLoginResponse(ResultCodes.WRONG_USER, null);
            }

            if (!userLogin.getHashedPassword().equalsIgnoreCase(user.getHashedPassword())) {
                return new UserLoginResponse(ResultCodes.WRONG_USER_PASSWORD, null);
            }

            if (!SecurityUtil.generateKeyFromArray(userLogin.getAccountID(), userLogin.getLogin(), userLogin.getHashedPassword(),
                    account.getPrivateKey()).equalsIgnoreCase(userLogin.getCheckSum())) {
                return new UserLoginResponse(ResultCodes.WRONG_CHECKSUM, null);
            }

            String token = RandomStringUtils.randomAlphanumeric(20);

            AdminUserToken adminUserToken = new AdminUserToken(token, user, new Date());

            cacheProvider.putAdminUserToken(token, adminUserToken);

            return new UserLoginResponse(ResultCodes.OK_RESPONSE, token);
        } catch (Exception e) {
            log.error("Admin login controller error", e);
            return new UserLoginResponse(ResultCodes.GENERAL_ERROR, null);
        }
    }
}



