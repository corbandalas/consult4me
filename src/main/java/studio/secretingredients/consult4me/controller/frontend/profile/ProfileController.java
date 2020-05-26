package studio.secretingredients.consult4me.controller.frontend.profile;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.CacheProvider;
import studio.secretingredients.consult4me.authorization.customer.CustomerAuthorized;
import studio.secretingredients.consult4me.authorization.customer.CustomerToken;
import studio.secretingredients.consult4me.authorization.specialist.SpecialistAuthorized;
import studio.secretingredients.consult4me.authorization.specialist.SpecialistToken;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.frontend.profile.dto.*;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistSpecialisation;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.domain.Specialisation;
import studio.secretingredients.consult4me.domain.Specialist;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.CustomerService;
import studio.secretingredients.consult4me.service.SpecialisationService;
import studio.secretingredients.consult4me.service.SpecialistService;

@RestController
@Slf4j
public class ProfileController {

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    CacheProvider cacheProvider;

    @Autowired
    SpecialisationService specialisationService;


    @PostMapping(
            value = "/frontend/customer/get", consumes = "application/json", produces = "application/json")
    @CustomerAuthorized
    public CustomerGetResponse get(@RequestBody CustomerGet request) {

        CustomerToken customerToken = cacheProvider.getCustomerToken(request.getToken());

        Customer customer = customerToken.getCustomer();

        return new CustomerGetResponse(ResultCodes.OK_RESPONSE, customerService.findCustomerByEmail(customer.getEmail()).get());
    }

    @PostMapping(
            value = "/frontend/specialist/get", consumes = "application/json", produces = "application/json")
    @SpecialistAuthorized
    public SpecialistGetResponse get(@RequestBody SpecialistGet request) {

        SpecialistToken specialistToken = cacheProvider.getSpecialistToken(request.getToken());

        Specialist specialist = specialistToken.getSpecialist();

        return new SpecialistGetResponse(ResultCodes.OK_RESPONSE, specialistService.findSpecialistByEmail(specialist.getEmail()).get());
    }

    @PostMapping(
            value = "/frontend/specialist/update", consumes = "application/json", produces = "application/json")
    @SpecialistAuthorized
    public SpecialistGetResponse update(@RequestBody SpecialistUpdate request) {

        try {

            if (request == null
                    || StringUtils.isBlank(request.getDescriptionDetailed())
                    || StringUtils.isBlank(request.getDescriptionShort())
                    || StringUtils.isBlank(request.getEducation())
                    || StringUtils.isBlank(request.getPan())
                    || request.getPriceHour() > 0
                    || StringUtils.isBlank(request.getCurrency())
                    || StringUtils.isBlank(request.getSocialProfile())
                    || StringUtils.isBlank(request.getFirstName())
                    || StringUtils.isBlank(request.getLastName())
                    || StringUtils.isBlank(request.getPhone())
            ) {
                return new SpecialistGetResponse(ResultCodes.WRONG_REQUEST, null);
            }

            SpecialistToken specialistToken = cacheProvider.getSpecialistToken(request.getToken());

            Specialist specialist = specialistToken.getSpecialist();

            specialist.setPriceHour(request.getPriceHour());
            specialist.setSocialProfile(request.getSocialProfile());
            specialist.setVideo(request.getVideo());
            specialist.setDescriptionShort(request.getDescriptionShort());
            specialist.setDescriptionDetailed(request.getDescriptionDetailed());
            specialist.setEducation(request.getEducation());
            specialist.setPhoto(request.getPhoto());
            specialist.setPhone(request.getPhone());
            specialist.setPan(request.getPan());
            specialist.setCurrency(request.getCurrency());
            specialist.setBirthDate(request.getBirthDate());
            if (StringUtils.isBlank(request.getHashedPassword())) {
                specialist.setHashedPassword(request.getHashedPassword());
            }

//            if (request.getSpecialisations() != null && request.getSpecialisations().size() > 0) {
//                specialist.setSpecialisations(request.getSpecialisations());
//            }

            if (request.getSpecialisations() != null && request.getSpecialisations().size() > 0) {
                for (SpecialistSpecialisation specialistSpecialisation: request.getSpecialisations()) {
                    Specialisation specialisation = specialisationService.findById(specialistSpecialisation.getId());

                    specialisation.getSpecialists().add(specialist);
                }
            }

            specialist = specialistService.save(specialist);


            return new SpecialistGetResponse(ResultCodes.OK_RESPONSE, specialist);

        } catch (Exception e) {
            log.error("Profile controller", e);
        }

        return new SpecialistGetResponse(ResultCodes.GENERAL_ERROR, null);
    }

}



