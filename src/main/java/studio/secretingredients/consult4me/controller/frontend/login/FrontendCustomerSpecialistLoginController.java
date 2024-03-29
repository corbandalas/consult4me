package studio.secretingredients.consult4me.controller.frontend.login;

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
import studio.secretingredients.consult4me.authorization.specialist.SpecialistToken;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.frontend.login.dto.CustomerLogin;
import studio.secretingredients.consult4me.controller.frontend.login.dto.CustomerLoginResponse;
import studio.secretingredients.consult4me.controller.frontend.login.dto.SpecialistLogin;
import studio.secretingredients.consult4me.controller.frontend.login.dto.SpecialistLoginResponse;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.domain.Specialist;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.CustomerService;
import studio.secretingredients.consult4me.service.SpecialistService;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
public class FrontendCustomerSpecialistLoginController {

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountService accountService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    CacheProvider cacheProvider;

    @PostMapping(
            value = "frontend/customer/login", consumes = "application/json", produces = "application/json")
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

            log.info("Calculated checksum: " + SecurityUtil.generateKeyFromArray(userLogin.getAccountID(), userLogin.getLogin(), userLogin.getHashedPassword(),
                    account.getPrivateKey()));

            if (!SecurityUtil.generateKeyFromArray(userLogin.getAccountID(), userLogin.getLogin(), userLogin.getHashedPassword(),
                    account.getPrivateKey()).equalsIgnoreCase(userLogin.getCheckSum())) {
                return new CustomerLoginResponse(ResultCodes.WRONG_CHECKSUM, null);
            }

            String token = RandomStringUtils.randomAlphanumeric(20);

            CustomerToken customerToken = new CustomerToken(token, customer, new Date(), account);

            cacheProvider.putCustomerToken(token, customerToken);

            return new CustomerLoginResponse(ResultCodes.OK_RESPONSE, token);

        } catch (Exception e) {
            log.error("Error", e);
            return new CustomerLoginResponse(ResultCodes.GENERAL_ERROR, null);
        }
    }

    @PostMapping(
            value = "/frontend/specialist/login", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "SHA256(accountID+login++hashedPassword+privateKey)"
                    , name = "checksum")})
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

            Optional<Specialist> specialistByEmail = specialistService.findSpecialistByEmail(userLogin.getLogin());

            if (!specialistByEmail.isPresent()) {
                return new SpecialistLoginResponse(ResultCodes.WRONG_USER, null);
            }

            Specialist customer = specialistByEmail.get();

            if (!customer.isActive()) {
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

            SpecialistToken customerToken = new SpecialistToken(token, customer, new Date(), account);

            cacheProvider.putSpecialistToken(token, customerToken);

            return new SpecialistLoginResponse(ResultCodes.OK_RESPONSE, token);

        } catch (Exception e) {
            log.error("Error", e);
            return new SpecialistLoginResponse(ResultCodes.GENERAL_ERROR, null);
        }
    }
}



