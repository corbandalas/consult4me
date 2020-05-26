package studio.secretingredients.consult4me.controller.frontend.register;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.frontend.register.dto.CustomerChannel;
import studio.secretingredients.consult4me.controller.frontend.register.dto.CustomerRegister;
import studio.secretingredients.consult4me.controller.frontend.register.dto.CustomerRegisterResponse;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.Channel;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.CustomerService;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class CustomerRegisterController {

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

//    @Autowired
//    CacheProvider cacheProvider;

    @PostMapping(
            value = "/frontend/customer/register", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "SHA256(accountID+email+phone+privateKey)"
                    , name = "checksum")})
    public CustomerRegisterResponse login(@RequestBody CustomerRegister customerRegister) {

        try {

            if (customerRegister == null || StringUtils.isBlank(customerRegister.getAccountID())
//                    || StringUtils.isBlank(customerRegister.getHashedPassword())
                    || StringUtils.isBlank(customerRegister.getCheckSum())
                    || StringUtils.isBlank(customerRegister.getEmail())
                    || StringUtils.isBlank(customerRegister.getFirstName())
                    || StringUtils.isBlank(customerRegister.getLastName())
                    || StringUtils.isBlank(customerRegister.getPhone())
            ) {
                return new CustomerRegisterResponse(ResultCodes.WRONG_REQUEST);
            }

            Account account = accountService.findAccountByID(Integer.parseInt(customerRegister.getAccountID()));

            if (account == null || !account.isActive()) {
                return new CustomerRegisterResponse(ResultCodes.WRONG_ACCOUNT);
            }

            Optional<Customer> customerByEmail = customerService.findCustomerByEmail(customerRegister.getEmail());


            if (customerByEmail.isPresent()) {
                return new CustomerRegisterResponse(ResultCodes.ALREADY_REGISTERED);
            }

            if (!SecurityUtil.generateKeyFromArray(customerRegister.getAccountID(), customerRegister.getEmail(), /*customerRegister.getHashedPassword(),*/ customerRegister.getPhone(),
                    account.getPrivateKey()).equalsIgnoreCase(customerRegister.getCheckSum())) {
                return new CustomerRegisterResponse(ResultCodes.WRONG_CHECKSUM);
            }

            Customer customer = new Customer();

            customer.setActive(true);
            customer.setAgree(true);
            customer.setEmail(customerRegister.getEmail());
            customer.setFirstName(customerRegister.getFirstName());
            customer.setLastName(customerRegister.getLastName());

            String generatedPassword = RandomStringUtils.randomNumeric(6);
            customer.setHashedPassword(SecurityUtil.generateKeyFromArray(generatedPassword));

            log.info("Generated password for customer [" + customerRegister.getEmail() + "] = " + generatedPassword);

            //TODO: Sending SMS/email with generated password

            customer.setPhone(customerRegister.getPhone());
            customer.setRegistrationDate(new Date());

            List<Channel> list = new ArrayList<>();

            if (customerRegister.getChannels() != null && customerRegister.getChannels().size() > 0) {

                for (CustomerChannel customerChannel : customerRegister.getChannels()) {
                    Channel channel = new Channel();

                    channel.setChannelCategory(customerChannel.getChannelCategory());
                    channel.setAccount(customerChannel.getAccount());

                    list.add(channel);
                }

            }

            log.info("List of channels size " + list.size());

            customer.setChannels(list);

            customerService.save(customer);

//            CustomerToken customerToken = new CustomerToken(token, customer, new Date());

//            cacheProvider.putCustomerToken(token, customerToken);

            return new CustomerRegisterResponse(ResultCodes.OK_RESPONSE);

        } catch (Exception e) {
            log.error("Error", e);
            return new CustomerRegisterResponse(ResultCodes.GENERAL_ERROR);
        }
    }
}



