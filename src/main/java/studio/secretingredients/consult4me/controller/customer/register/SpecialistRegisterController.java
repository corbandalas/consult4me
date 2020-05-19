package studio.secretingredients.consult4me.controller.customer.register;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.authorization.SpecialistToken;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.customer.register.dto.CustomerRegister;
import studio.secretingredients.consult4me.controller.customer.register.dto.CustomerRegisterResponse;
import studio.secretingredients.consult4me.controller.customer.register.dto.SpecialistRegister;
import studio.secretingredients.consult4me.controller.customer.register.dto.SpecialistRegisterResponse;
import studio.secretingredients.consult4me.domain.*;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.CustomerService;
import studio.secretingredients.consult4me.service.SpecialistService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class SpecialistRegisterController {

    @Autowired
    AccountService accountService;

    @Autowired
    SpecialistService specialistService;

//    @Autowired
//    CacheProvider cacheProvider;

    @PostMapping(
            value = "/specialist/register", consumes = "application/json", produces = "application/json")
    public SpecialistRegisterResponse login(@RequestBody SpecialistRegister customerRegister) {

        try {

            if (customerRegister == null || StringUtils.isBlank(customerRegister.getAccountID())
                    || StringUtils.isBlank(customerRegister.getHashedPassword())
                    || StringUtils.isBlank(customerRegister.getCheckSum())
                    || StringUtils.isBlank(customerRegister.getEmail())
                    || StringUtils.isBlank(customerRegister.getDescriptionDetailed())
                    || StringUtils.isBlank(customerRegister.getDescriptionShort())
                    || StringUtils.isBlank(customerRegister.getEducation())
                    || StringUtils.isBlank(customerRegister.getPan())
                    || customerRegister.getPriceHour() > 0
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

            if (specialistService.findSpecialistByEmail(customerRegister.getEmail()) != null) {
                return new SpecialistRegisterResponse(ResultCodes.ALREADY_REGISTERED);
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
            specialist.setHashedPassword(generatedPassword);

            log.info("Generated password for customer [" + customerRegister.getEmail() + "] = " + generatedPassword);

            //TODO: Sending SMS/email with generated password

            specialist.setPhone(customerRegister.getPhone());
            specialist.setRegistrationDate(new Date());

            List<Specialisation> list = new ArrayList<>();

            if (customerRegister.getSpecialisations() != null && customerRegister.getSpecialisations().size() > 0) {
                customerRegister.getSpecialisations().stream().map(res -> {
                    Specialisation specialisation = new Specialisation();

                    specialisation.setSpecialisationCategory(res.getSpecialisationCategory());
                    specialisation.setSpecialisationType(res.getSpecialisationType());

                    list.add(specialisation);

                    return specialisation;
                });
            }

            specialist.setSpecialisations(list);

            specialistService.save(specialist);

//            CustomerToken customerToken = new CustomerToken(token, customer, new Date());

//            cacheProvider.putCustomerToken(token, customerToken);

            return new SpecialistRegisterResponse(ResultCodes.OK_RESPONSE);

        } catch (Exception e) {
            log.error("Error", e);
            return new SpecialistRegisterResponse(ResultCodes.GENERAL_ERROR);
        }
    }
}


