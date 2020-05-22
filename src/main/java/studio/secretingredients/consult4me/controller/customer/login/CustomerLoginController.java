package studio.secretingredients.consult4me.controller.customer.login;

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
import studio.secretingredients.consult4me.authorization.customer.CustomerToken;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.customer.login.dto.CustomerLogin;
import studio.secretingredients.consult4me.controller.customer.login.dto.CustomerLoginResponse;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.CustomerService;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
public class CustomerLoginController {

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CacheProvider cacheProvider;

    @PostMapping(
            value = "/customer/login", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "SHA256(accountID+login+hashedPassword+privateKey)"
                    , name = "checksum")})
    public CustomerLoginResponse login(@RequestBody CustomerLogin userLogin) {

        try {

            if (userLogin == null || StringUtils.isBlank(userLogin.getAccountID())
                    || StringUtils.isBlank(userLogin.getHashedPassword())
                    || StringUtils.isBlank(userLogin.getCheckSum())
                    || StringUtils.isBlank(userLogin.getLogin())
            ) {
                return new CustomerLoginResponse(ResultCodes.WRONG_REQUEST, null);
            }

            Account account = accountService.findAccountByID(Integer.parseInt(userLogin.getAccountID()));

            if (account == null || !account.isActive()) {
                return new CustomerLoginResponse(ResultCodes.WRONG_ACCOUNT, null);
            }

            Optional<Customer> customerByEmail = customerService.findCustomerByEmail(userLogin.getLogin());

            if (!customerByEmail.isPresent()) {
                return new CustomerLoginResponse(ResultCodes.WRONG_USER, null);
            }

            Customer customer = customerByEmail.get();

            if (!customer.isActive()) {
                return new CustomerLoginResponse(ResultCodes.WRONG_USER, null);
            }

            if (!userLogin.getHashedPassword().equalsIgnoreCase(customer.getHashedPassword())) {
                return new CustomerLoginResponse(ResultCodes.WRONG_USER_PASSWORD, null);
            }

            if (!SecurityUtil.generateKeyFromArray(userLogin.getAccountID(), userLogin.getLogin(), userLogin.getHashedPassword(),
                    account.getPrivateKey()).equalsIgnoreCase(userLogin.getCheckSum())) {
                return new CustomerLoginResponse(ResultCodes.WRONG_CHECKSUM, null);
            }

            String token = RandomStringUtils.randomAlphanumeric(20);

            CustomerToken customerToken = new CustomerToken(token, customer, new Date());

            cacheProvider.putCustomerToken(token, customerToken);

            return new CustomerLoginResponse(ResultCodes.OK_RESPONSE, token);

        } catch (Exception e) {
            log.error("Error", e);
            return new CustomerLoginResponse(ResultCodes.GENERAL_ERROR, null);
        }
    }
}



