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
import studio.secretingredients.consult4me.domain.*;
import studio.secretingredients.consult4me.service.*;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.*;

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

    @Autowired
    SpecialistTimeService specialistTimeService;


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

            Set<Specialisation> specialisations = new HashSet<>();


            if (request.getSpecialisations() != null && request.getSpecialisations().size() > 0) {
                for (SpecialistSpecialisation specialistSpecialisation: request.getSpecialisations()) {

                    Specialisation specialisation = specialisationService.findById(specialistSpecialisation.getId());

                    specialisations.add(specialisation);

                }

                specialist.setSpecialisations(specialisations);
            }

            Specialist save = specialistService.save(specialist);


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
                    || request.getPriceHour() <= 0
                    || StringUtils.isBlank(request.getCurrency())
                    || StringUtils.isBlank(request.getSocialProfile())
                    || StringUtils.isBlank(request.getFirstName())
                    || StringUtils.isBlank(request.getLastName())
                    || StringUtils.isBlank(request.getPhone())
                    ) {
                return new SpecialistGetResponse(ResultCodes.WRONG_REQUEST, null, null);
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
            if (StringUtils.isNotBlank(request.getHashedPassword())) {
                specialist.setHashedPassword(request.getHashedPassword());
            }

//            if (request.getSpecialisations() != null && request.getSpecialisations().size() > 0) {
//                specialist.setSpecialisations(request.getSpecialisations());
//            }

            Set<Specialisation> specialisations = new HashSet<>();

            if (request.getSpecialisations() != null && request.getSpecialisations().size() > 0) {
                for (SpecialistSpecialisation specialistSpecialisation: request.getSpecialisations()) {

                    Specialisation specialisation = specialisationService.findById(specialistSpecialisation.getId());

                    specialisations.add(specialisation);

                }

                specialist.setSpecialisations(specialisations);
            }

            specialist = specialistService.save(specialist);


            return new SpecialistGetResponse(ResultCodes.OK_RESPONSE, specialist, specialist.getSpecialisations());

        } catch (Exception e) {
            log.error("Profile controller", e);
        }

        return new SpecialistGetResponse(ResultCodes.GENERAL_ERROR, null, null);
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
            AdminRole.ROLE_ADMIN_EDIT_CATEGORIES
    })
    public AdminSpecialistCategoriesResponse specialistCategories(@RequestBody AdminSpecialistCategories request) {

        Optional<Specialist> specialistByEmail = specialistService.findSpecialistByEmail(request.getSpecialistEmail());

        Specialist specialist = specialistByEmail.get();

        return new AdminSpecialistCategoriesResponse(ResultCodes.OK_RESPONSE, specialist.getSpecialisations());
    }

    @PostMapping(
            value = "/admin/specialist/addTime", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_EDIT_SPECIALIST
    })
    public AdminSpecialistTimeResponse addTimeToSpecialist(@RequestBody AdminSpecialistAddTime request) {

        Optional<Specialist> specialistByEmail = specialistService.findSpecialistByEmail(request.getSpecialistEmail());

        SpecialistTime specialistTime = new SpecialistTime();

        specialistTime.setSpecialist(specialistByEmail.get());
        specialistTime.setStartDate(request.getStartDate());
        specialistTime.setEndDate(request.getEndDate());
        specialistTime.setFree(true);

        SpecialistTime save = specialistTimeService.save(specialistTime);

        return new AdminSpecialistTimeResponse(ResultCodes.OK_RESPONSE, save);
    }


    @PostMapping(
            value = "/admin/specialist/getTime", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_EDIT_SPECIALIST
    })
    public AdminSpecialistFindTimeResponse findSpecialistTime(@RequestBody AdminSpecialistFindTime request) {

        Optional<Specialist> specialistByEmail = specialistService.findSpecialistByEmail(request.getSpecialistEmail());

        List<SpecialistTime> specialistTime = null;

        if (request.getStartSearchPeriod() != null && request.getEndSearchPeriod() != null ) {
            specialistTime = specialistTimeService.findStartDateAfterStartAndEndDateBeforeEndBySpecialist(request.getStartSearchPeriod(),
                    request.getEndSearchPeriod(), specialistByEmail.get());
        } else {
            specialistTime = specialistTimeService.findSpecialistTime(specialistByEmail.get());
        }

        return new AdminSpecialistFindTimeResponse(ResultCodes.OK_RESPONSE, specialistTime);
    }


    @PostMapping(
            value = "/admin/specialist/updateTime", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_EDIT_SPECIALIST
    })
    public AdminSpecialistTimeResponse updateSpecialistTime(@RequestBody AdminSpecialistUpdateTime request) {

        SpecialistTime specialistTime = specialistTimeService.findById(request.getId());

        specialistTime.setStartDate(request.getStartDate());
        specialistTime.setEndDate(request.getEndDate());
        specialistTime.setFree(request.isFree());

        SpecialistTime save = specialistTimeService.save(specialistTime);

        return new AdminSpecialistTimeResponse(ResultCodes.OK_RESPONSE, save);
    }


}



