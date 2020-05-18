package studio.secretingredients.consult4me.controller.customer.login;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.CacheProvider;
import studio.secretingredients.consult4me.authorization.CustomerToken;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.customer.login.dto.SpecialistLogin;
import studio.secretingredients.consult4me.controller.customer.login.dto.SpecialistLoginResponse;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.Specialist;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.SpecialistService;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.Date;

@Slf4j
@RestController
public class SpecialistLoginController {

    @Autowired
    AccountService accountService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    CacheProvider cacheProvider;

    @PostMapping(
            value = "/specialist/login", consumes = "application/json", produces = "application/json")
    public SpecialistLoginResponse login(@RequestBody SpecialistLogin userLogin) {

        try {

            if (userLogin == null || StringUtils.isBlank(userLogin.getAccountID())
                    || StringUtils.isBlank(userLogin.getHashedPassword())
                    || StringUtils.isBlank(userLogin.getCheckSum())
                    || StringUtils.isBlank(userLogin.getLogin())
            ) {
                return new SpecialistLoginResponse(ResultCodes.WRONG_REQUEST, null);
            }

            Account account = accountService.findAccountByID(Integer.parseInt(userLogin.getAccountID()));

            if (account == null || !account.isActive()) {
                return new SpecialistLoginResponse(ResultCodes.WRONG_ACCOUNT, null);
            }

            Specialist customer = specialistService.findSpecialistByEmail(userLogin.getLogin());

            if (customer == null || !customer.isActive()) {
                return new SpecialistLoginResponse(ResultCodes.WRONG_USER, null);
            }

            if (!userLogin.getHashedPassword().equalsIgnoreCase(customer.getHashedPassword())) {
                return new SpecialistLoginResponse(ResultCodes.WRONG_USER_PASSWORD, null);
            }

            if (!SecurityUtil.generateKeyFromArray(userLogin.getAccountID(), userLogin.getLogin(), userLogin.getHashedPassword(),
                    account.getPrivateKey()).equalsIgnoreCase(userLogin.getCheckSum())) {
                return new SpecialistLoginResponse(ResultCodes.WRONG_CHECKSUM, null);
            }

            String token = RandomStringUtils.randomAlphanumeric(20);

            CustomerToken customerToken = new CustomerToken(token, customer, new Date());

            cacheProvider.putCustomerToken(token, customerToken);

            return new SpecialistLoginResponse(ResultCodes.OK_RESPONSE, token);

        } catch (Exception e) {
            log.error("Error", e);
            return new SpecialistLoginResponse(ResultCodes.GENERAL_ERROR, null);
        }
    }
}


