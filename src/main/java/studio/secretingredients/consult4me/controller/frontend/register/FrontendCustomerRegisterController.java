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
import studio.secretingredients.consult4me.controller.frontend.register.dto.*;
import studio.secretingredients.consult4me.domain.*;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.CustomerService;
import studio.secretingredients.consult4me.service.SpecialisationService;
import studio.secretingredients.consult4me.service.SpecialistService;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class FrontendCustomerRegisterController {

    @Autowired
    CustomerService customerService;

//    @Autowired
//    CacheProvider cacheProvider;

    @Autowired
    AccountService accountService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    SpecialisationService specialisationService;

    @PostMapping(
            value = "/frontend/customer/register", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "SHA256(accountID+email+phone+privateKey)"
                    , name = "checksum")})
    public CustomerRegisterResponse customerRegister(@RequestBody CustomerRegister customerRegister) {

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


    @PostMapping(
            value = "/frontend/specialist/register", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "SHA256(accountID+email+phone+privateKey)"
                    , name = "checksum")})
    public SpecialistRegisterResponse specialistRegister(@RequestBody SpecialistRegister customerRegister) {

        try {

            if (customerRegister == null || StringUtils.isBlank(customerRegister.getAccountID())
//                    || StringUtils.isBlank(customerRegister.getHashedPassword())
                    || StringUtils.isBlank(customerRegister.getCheckSum())
                    || StringUtils.isBlank(customerRegister.getEmail())
                    || StringUtils.isBlank(customerRegister.getDescriptionDetailed())
                    || StringUtils.isBlank(customerRegister.getDescriptionShort())
                    || StringUtils.isBlank(customerRegister.getEducation())
                    || StringUtils.isBlank(customerRegister.getPan())
                    || customerRegister.getPriceHour() <= 0
                    || StringUtils.isBlank(customerRegister.getCurrency())
                    || StringUtils.isBlank(customerRegister.getSocialProfile())
                    || StringUtils.isBlank(customerRegister.getFirstName())
                    || StringUtils.isBlank(customerRegister.getLastName())
                    || StringUtils.isBlank(customerRegister.getPhone())
                    ) {
                return new SpecialistRegisterResponse(ResultCodes.WRONG_REQUEST);
            }


            Account account = accountService.findAccountByID(Integer.parseInt(customerRegister.getAccountID()));

            if (account == null || !account.isActive()) {
                return new SpecialistRegisterResponse(ResultCodes.WRONG_ACCOUNT);
            }

            Optional<Specialist> specialistByEmail = specialistService.findSpecialistByEmail(customerRegister.getEmail());

            if (specialistByEmail.isPresent()) {
                return new SpecialistRegisterResponse(ResultCodes.ALREADY_REGISTERED);
            }

            if (!SecurityUtil.generateKeyFromArray(customerRegister.getAccountID(), customerRegister.getEmail(), customerRegister.getPhone(),
                    account.getPrivateKey()).equalsIgnoreCase(customerRegister.getCheckSum())) {
                return new SpecialistRegisterResponse(ResultCodes.WRONG_CHECKSUM);
            }


            Specialist specialist = new Specialist();

            specialist.setActive(false);
            specialist.setAgree(true);
            specialist.setEmail(customerRegister.getEmail());
            specialist.setPhoto(customerRegister.getPhoto());
            specialist.setEducation(customerRegister.getEducation());
            specialist.setDescriptionDetailed(customerRegister.getDescriptionDetailed());
            specialist.setDescriptionShort(customerRegister.getDescriptionShort());
            specialist.setVideo(customerRegister.getVideo());
            specialist.setSocialProfile(customerRegister.getSocialProfile());
            specialist.setFirstName(customerRegister.getFirstName());
            specialist.setLastName(customerRegister.getLastName());
            specialist.setBirthDate(customerRegister.getBirthDate());
            specialist.setCurrency(customerRegister.getCurrency());
            specialist.setPriceHour(customerRegister.getPriceHour());
            specialist.setPan(customerRegister.getPan());

            String generatedPassword = RandomStringUtils.randomNumeric(6);
            specialist.setHashedPassword(SecurityUtil.generateKeyFromArray(generatedPassword));

            log.info("Generated password for specialist [" + customerRegister.getEmail() + "] = " + generatedPassword);

            //TODO: Sending SMS/email with generated password

            specialist.setPhone(customerRegister.getPhone());
            specialist.setRegistrationDate(new Date());


            Specialist save = specialistService.save(specialist);

            if (customerRegister.getSpecialisations() != null && customerRegister.getSpecialisations().size() > 0) {
                for (SpecialistSpecialisation specialistSpecialisation: customerRegister.getSpecialisations()) {
                    Specialisation specialisation = specialisationService.findById(specialistSpecialisation.getId());

                    specialisation.getSpecialists().add(save);

                    specialisationService.save(specialisation);
                }
            }


//            CustomerToken customerToken = new CustomerToken(token, customer, new Date());

//            cacheProvider.putCustomerToken(token, customerToken);

            return new SpecialistRegisterResponse(ResultCodes.OK_RESPONSE);

        } catch (Exception e) {
            log.error("Error", e);
            return new SpecialistRegisterResponse(ResultCodes.GENERAL_ERROR);
        }
    }
}



