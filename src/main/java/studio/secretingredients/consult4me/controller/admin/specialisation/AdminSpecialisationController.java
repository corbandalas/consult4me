package studio.secretingredients.consult4me.controller.admin.specialisation;

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
import studio.secretingredients.consult4me.authorization.admin.AdminUserAuthorized;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.admin.customer.dto.*;
import studio.secretingredients.consult4me.controller.admin.specialisation.dto.*;
import studio.secretingredients.consult4me.controller.frontend.profile.dto.CategoriesList;
import studio.secretingredients.consult4me.controller.frontend.profile.dto.CategoriesListResponse;
import studio.secretingredients.consult4me.controller.frontend.profile.dto.SpecialistGetResponse;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistRegisterResponse;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistSpecialisation;
import studio.secretingredients.consult4me.domain.*;
import studio.secretingredients.consult4me.service.*;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.*;

@RestController
@Slf4j
public class AdminSpecialisationController {

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
            value = "/admin/specialisation/type/add", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_EDIT_SPECIALISATION
    })
    public AddSpecialistTypeResponse addSpecialisationType(@RequestBody AddSpecialistType request) {


        if (request == null
                || StringUtils.isBlank(request.getDescription())
                || StringUtils.isBlank(request.getName())
        ) {
            return new AddSpecialistTypeResponse(ResultCodes.WRONG_REQUEST, null);
        }
        SpecialisationType specialisationType = new SpecialisationType();


        specialisationType.setDescription(request.getDescription());
        specialisationType.setName(request.getName());

        return new AddSpecialistTypeResponse(ResultCodes.OK_RESPONSE, specialisationService.addType(specialisationType));
    }

    @PostMapping(
            value = "/admin/specialisation/category/add", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_EDIT_SPECIALISATION
    })
    public AddSpecialistCategoryResponse addSpecialisationCategory(@RequestBody AddSpecialistType request) {


        if (request == null
                || StringUtils.isBlank(request.getDescription())
                || StringUtils.isBlank(request.getName())
        ) {
            return new AddSpecialistCategoryResponse(ResultCodes.WRONG_REQUEST, null);
        }
        SpecialisationCategory specialisationCategory = new SpecialisationCategory();

        specialisationCategory.setDescription(request.getDescription());
        specialisationCategory.setName(request.getName());

        return new AddSpecialistCategoryResponse(ResultCodes.OK_RESPONSE, specialisationService.addCategory(specialisationCategory));
    }

    @PostMapping(
            value = "/admin/specialisation/add", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_EDIT_SPECIALISATION
    })
    public AddSpecialisationResponse addSpecialisation(@RequestBody AddSpecialisation request) {


        if (request == null
                || request.getCategoryID() == null
                || request.getTypeID() == null
        ) {
            return new AddSpecialisationResponse(ResultCodes.WRONG_REQUEST, null);
        }
        Specialisation specialisation = new Specialisation();

        SpecialisationType specialisationType = specialisationService.findTypeById(request.getTypeID());

        SpecialisationCategory specialisationCategory = specialisationService.findCategoryById(request.getCategoryID());

        specialisation.setSpecialisationType(specialisationType);
        specialisation.setSpecialisationCategory(specialisationCategory);


        return new AddSpecialisationResponse(ResultCodes.OK_RESPONSE, specialisationService.save(specialisation));
    }


    @PostMapping(
            value = "/admin/specialisation/list", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_EDIT_SPECIALISATION
    })
    public CategoriesListResponse categoriesList(@RequestBody SpecialisationList categoriesList) {

        try {

            return new CategoriesListResponse(ResultCodes.OK_RESPONSE, specialisationService.findAll());

        } catch (Exception e) {
            log.error("Error", e);
            return new CategoriesListResponse(ResultCodes.GENERAL_ERROR, null);
        }
    }


}



