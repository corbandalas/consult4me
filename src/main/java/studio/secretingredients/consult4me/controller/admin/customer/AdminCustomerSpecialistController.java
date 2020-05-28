package studio.secretingredients.consult4me.controller.admin.customer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.CacheProvider;
import studio.secretingredients.consult4me.authorization.admin.AdminUserAuthorized;
import studio.secretingredients.consult4me.authorization.customer.CustomerAuthorized;
import studio.secretingredients.consult4me.authorization.specialist.SpecialistAuthorized;
import studio.secretingredients.consult4me.authorization.specialist.SpecialistToken;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.admin.customer.dto.*;
import studio.secretingredients.consult4me.controller.frontend.profile.dto.SpecialistGetResponse;
import studio.secretingredients.consult4me.controller.frontend.profile.dto.SpecialistUpdate;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistRegisterResponse;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistSpecialisation;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.AdminRole;
import studio.secretingredients.consult4me.domain.Specialisation;
import studio.secretingredients.consult4me.domain.Specialist;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.CustomerService;
import studio.secretingredients.consult4me.service.SpecialisationService;
import studio.secretingredients.consult4me.service.SpecialistService;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class AdminCustomerSpecialistController {

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
            value = "/admin/specialist/list", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_SPECIALIST_LIST
    })
    public SpecialistListResponse specialistList(@RequestBody CustomerSpecialistList request) {

        return new SpecialistListResponse(ResultCodes.OK_RESPONSE, specialistService.findAll());
    }

    @PostMapping(
            value = "/admin/specialist/create", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_ADD_SPECIALIST
    })
    public SpecialistRegisterResponse specialistCreate(@RequestBody AdminSpecialistRegister request) {

        try {

            if (request == null
                    || StringUtils.isBlank(request.getEmail())
                    || StringUtils.isBlank(request.getDescriptionDetailed())
                    || StringUtils.isBlank(request.getDescriptionShort())
                    || StringUtils.isBlank(request.getEducation())
                    || StringUtils.isBlank(request.getPan())
                    || request.getPriceHour() <= 0
                    || StringUtils.isBlank(request.getCurrency())
                    || StringUtils.isBlank(request.getSocialProfile())
                    || StringUtils.isBlank(request.getFirstName())
                    || StringUtils.isBlank(request.getLastName())
                    || StringUtils.isBlank(request.getPhone())
                    ) {
                return new SpecialistRegisterResponse(ResultCodes.WRONG_REQUEST);
            }


            Optional<Specialist> specialistByEmail = specialistService.findSpecialistByEmail(request.getEmail());

            if (specialistByEmail.isPresent()) {
                return new SpecialistRegisterResponse(ResultCodes.ALREADY_REGISTERED);
            }

            Specialist specialist = new Specialist();

            specialist.setActive(request.isActive());
            specialist.setAgree(true);
            specialist.setEmail(request.getEmail());
            specialist.setPhoto(request.getPhoto());
            specialist.setEducation(request.getEducation());
            specialist.setDescriptionDetailed(request.getDescriptionDetailed());
            specialist.setDescriptionShort(request.getDescriptionShort());
            specialist.setVideo(request.getVideo());
            specialist.setSocialProfile(request.getSocialProfile());
            specialist.setFirstName(request.getFirstName());
            specialist.setLastName(request.getLastName());
            specialist.setBirthDate(request.getBirthDate());
            specialist.setCurrency(request.getCurrency());
            specialist.setPriceHour(request.getPriceHour());
            specialist.setPan(request.getPan());

            String generatedPassword = RandomStringUtils.randomNumeric(6);
            specialist.setHashedPassword(SecurityUtil.generateKeyFromArray(generatedPassword));

            log.info("Generated password for specialist [" + request.getEmail() + "] = " + generatedPassword);

            //TODO: Sending SMS/email with generated password

            specialist.setPhone(request.getPhone());
            specialist.setRegistrationDate(new Date());


            Specialist save = specialistService.save(specialist);

            if (request.getSpecialisations() != null && request.getSpecialisations().size() > 0) {
                for (SpecialistSpecialisation specialistSpecialisation: request.getSpecialisations()) {
                    Specialisation specialisation = specialisationService.findById(specialistSpecialisation.getId());

                    specialisation.getSpecialists().add(save);

                    specialisationService.save(specialisation);
                }
            }

            return new SpecialistRegisterResponse(ResultCodes.OK_RESPONSE);

        } catch (Exception e) {
            log.error("Error", e);
            return new SpecialistRegisterResponse(ResultCodes.GENERAL_ERROR);
        }
    }

    @PostMapping(
            value = "/admin/specialist/update", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_EDIT_SPECIALIST
    })
    public SpecialistGetResponse updateSpecialist(@RequestBody AdminSpecialistUpdate request) {

        try {

            if (request == null
                    || StringUtils.isBlank(request.getSpecialistEmail())
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

            Specialist specialist = specialistService.findSpecialistByEmail(request.getSpecialistEmail()).get();

            specialist.setActive(request.isActive());
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

                    specialisationService.save(specialisation);
                }
            }

            specialist = specialistService.save(specialist);


            return new SpecialistGetResponse(ResultCodes.OK_RESPONSE, specialist);

        } catch (Exception e) {
            log.error("Profile controller", e);
        }

        return new SpecialistGetResponse(ResultCodes.GENERAL_ERROR, null);
    }


    @PostMapping(
            value = "/admin/customer/list", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_CUSTOMER_LIST
    })
    public CustomerListResponse customerList(@RequestBody CustomerSpecialistList request) {

        return new CustomerListResponse(ResultCodes.OK_RESPONSE, customerService.findAll());
    }

    @PostMapping(
            value = "/admin/specialist/categories", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_CUSTOMER_LIST
    })
    public AdminSpecialistCategoriesResponse specialistCategories(@RequestBody AdminSpecialistCategories request) {

        Optional<Specialist> specialistByEmail = specialistService.findSpecialistByEmail(request.getSpecialistEmail());

        Specialist specialist = specialistByEmail.get();

        List<Specialisation> bySpecialist = specialisationService.findBySpecialist(specialist);

        return new AdminSpecialistCategoriesResponse(ResultCodes.OK_RESPONSE, bySpecialist);
    }



}



