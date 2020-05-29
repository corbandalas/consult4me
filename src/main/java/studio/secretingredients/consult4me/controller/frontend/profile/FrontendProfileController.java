package studio.secretingredients.consult4me.controller.frontend.profile;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.CacheProvider;
import studio.secretingredients.consult4me.authorization.admin.AdminUserAuthorized;
import studio.secretingredients.consult4me.authorization.customer.CustomerAuthorized;
import studio.secretingredients.consult4me.authorization.customer.CustomerToken;
import studio.secretingredients.consult4me.authorization.specialist.SpecialistAuthorized;
import studio.secretingredients.consult4me.authorization.specialist.SpecialistToken;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.admin.customer.dto.*;
import studio.secretingredients.consult4me.controller.frontend.profile.dto.*;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistSpecialisation;
import studio.secretingredients.consult4me.domain.*;
import studio.secretingredients.consult4me.service.*;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class FrontendProfileController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    CacheProvider cacheProvider;

    @Autowired
    AccountService accountService;

    @Autowired
    SpecialisationService specialisationService;

    @Autowired
    SpecialistTimeService specialistTimeService;


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
            value = "/frontend/categories/list", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "SHA256(accountID+privateKey)"
                    , name = "checksum")})
    public CategoriesListResponse login(@RequestBody CategoriesList categoriesList) {

        try {

            if (categoriesList == null || StringUtils.isBlank(categoriesList.getAccountID())
                    || StringUtils.isBlank(categoriesList.getCheckSum())
                    ) {
                return new CategoriesListResponse(ResultCodes.WRONG_REQUEST, null);
            }

            Account account = accountService.findAccountByID(Integer.parseInt(categoriesList.getAccountID()));

            if (account == null || !account.isActive()) {
                return new CategoriesListResponse(ResultCodes.WRONG_ACCOUNT, null);
            }


            if (!SecurityUtil.generateKeyFromArray(categoriesList.getAccountID(),
                    account.getPrivateKey()).equalsIgnoreCase(categoriesList.getCheckSum())) {
                return new CategoriesListResponse(ResultCodes.WRONG_CHECKSUM, null);
            }


            return new CategoriesListResponse(ResultCodes.OK_RESPONSE, specialisationService.findAll());

        } catch (Exception e) {
            log.error("Error", e);
            return new CategoriesListResponse(ResultCodes.GENERAL_ERROR, null);
        }
    }


    @PostMapping(
            value = "/frontend/specialist/addTime", consumes = "application/json", produces = "application/json")
    @SpecialistAuthorized
    public FrontendSpecialistTimeResponse addTimeToSpecialist(@RequestBody FrontendSpecialistAddTime request) {

        Specialist specialist = cacheProvider.getSpecialistToken(request.getToken()).getSpecialist();

        SpecialistTime specialistTime = new SpecialistTime();

        specialistTime.setSpecialist(specialist);
        specialistTime.setStartDate(request.getStartDate());
        specialistTime.setEndDate(request.getEndDate());
        specialistTime.setFree(true);

        SpecialistTime save = specialistTimeService.save(specialistTime);

        return new FrontendSpecialistTimeResponse(ResultCodes.OK_RESPONSE, save);
    }


    @PostMapping(
            value = "/frontend/specialist/getTime", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_EDIT_SPECIALIST
    })
    public AdminSpecialistFindTimeResponse findSpecialistTime(@RequestBody BaseTokenRequest request) {

        Specialist specialist = cacheProvider.getSpecialistToken(request.getToken()).getSpecialist();

        List<SpecialistTime> specialistTime = specialistTimeService.findSpecialistTime(specialist);

        return new AdminSpecialistFindTimeResponse(ResultCodes.OK_RESPONSE, specialistTime);
    }


    @PostMapping(
            value = "/frontend/specialist/updateTime", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_EDIT_SPECIALIST
    })
    public AdminSpecialistTimeResponse updateSpecialistTime(@RequestBody FrontendSpecialistUpdateTime request) {

        SpecialistTime specialistTime = specialistTimeService.findById(request.getId());

        specialistTime.setStartDate(request.getStartDate());
        specialistTime.setEndDate(request.getEndDate());
        specialistTime.setFree(request.isFree());

        SpecialistTime save = specialistTimeService.save(specialistTime);

        return new AdminSpecialistTimeResponse(ResultCodes.OK_RESPONSE, save);
    }

}



