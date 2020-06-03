package studio.secretingredients.consult4me.controller.admin.session;

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
import studio.secretingredients.consult4me.controller.admin.session.dto.SessionByCustomerList;
import studio.secretingredients.consult4me.controller.admin.session.dto.SessionBySpecialistList;
import studio.secretingredients.consult4me.controller.admin.session.dto.SessionByStateList;
import studio.secretingredients.consult4me.controller.admin.session.dto.SessionListResponse;
import studio.secretingredients.consult4me.controller.frontend.profile.dto.SpecialistGetResponse;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistRegisterResponse;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistSpecialisation;
import studio.secretingredients.consult4me.domain.*;
import studio.secretingredients.consult4me.service.*;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.*;

@RestController
@Slf4j
public class AdminSessionController {

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

    @Autowired
    SessionService sessionService;


    @PostMapping(
            value = "/admin/session/listbycustomer", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_VIEW_SESSIONS
    })
    public SessionListResponse sessionListByCustomer(@RequestBody SessionByCustomerList request) {

        try {

            if (request == null
                    || StringUtils.isBlank(request.getCustomerEmail())
                    ) {
                return new SessionListResponse(ResultCodes.WRONG_REQUEST, null);
            }

            Optional<Customer> customerByEmail = customerService.findCustomerByEmail(request.getCustomerEmail());

            if (!customerByEmail.isPresent()) {
                return new SessionListResponse(ResultCodes.WRONG_USER, null);
            }

            Customer customer = customerByEmail.get();

            return new SessionListResponse(ResultCodes.OK_RESPONSE, sessionService.findByCustomer(customer));


        } catch (Exception e) {
            log.error("Exception", e);
        }

        return new SessionListResponse(ResultCodes.GENERAL_ERROR, null);
    }

    @PostMapping(
            value = "/admin/session/listbyspecialist", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_VIEW_SESSIONS
    })
    public SessionListResponse sessionListBySpecialist(@RequestBody SessionBySpecialistList request) {

        try {

            if (request == null
                    || StringUtils.isBlank(request.getSpecialistEmail())
                    ) {
                return new SessionListResponse(ResultCodes.WRONG_REQUEST, null);
            }

            Optional<Specialist> customerByEmail = specialistService.findSpecialistByEmail(request.getSpecialistEmail());

            if (!customerByEmail.isPresent()) {
                return new SessionListResponse(ResultCodes.WRONG_USER, null);
            }

            Specialist specialist = customerByEmail.get();

            return new SessionListResponse(ResultCodes.OK_RESPONSE, sessionService.findBySpecialist(specialist));


        } catch (Exception e) {
            log.error("Exception", e);
        }

        return new SessionListResponse(ResultCodes.GENERAL_ERROR, null);
    }

    @PostMapping(
            value = "/admin/session/listbystate", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_VIEW_SESSIONS
    })
    public SessionListResponse sessionListByState(@RequestBody SessionByStateList request) {

        try {

            if (request == null
                    || request.getSessionState() == null
                    ) {
                return new SessionListResponse(ResultCodes.WRONG_REQUEST, null);
            }


            return new SessionListResponse(ResultCodes.OK_RESPONSE, sessionService.findByState(request.getSessionState()));


        } catch (Exception e) {
            log.error("Exception", e);
        }

        return new SessionListResponse(ResultCodes.GENERAL_ERROR, null);
    }


}



