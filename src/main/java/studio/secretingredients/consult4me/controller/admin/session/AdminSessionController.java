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
import studio.secretingredients.consult4me.controller.admin.session.dto.*;
import studio.secretingredients.consult4me.controller.frontend.profile.dto.SpecialistGetResponse;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistRegisterResponse;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistSpecialisation;
import studio.secretingredients.consult4me.domain.*;
import studio.secretingredients.consult4me.domain.SessionPayout;
import studio.secretingredients.consult4me.repository.SessionRepository;
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

    @Autowired
    SessionPayoutService sessionPayoutService;



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

    @PostMapping(
            value = "/admin/session/performpayout", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_PERFORM_PAYOUT
    })
    public SessionPayoutResponse sessionPayout(@RequestBody SessionPayoutRequest request) {

        try {

            if (request == null
                    ) {
                return new SessionPayoutResponse(ResultCodes.WRONG_REQUEST, null);
            }

            Optional<Session> session = sessionService.findByID(request.getSessionID());

            if (!session.isPresent()) {
                return new SessionPayoutResponse(ResultCodes.WRONG_SESSION, null);
            }

            if (!session.get().getSessionState().equals(SessionState.PAYED)) {
                return new SessionPayoutResponse(ResultCodes.WRONG_SESSION, null);
            }

            SessionPayout sessionPayout = sessionPayoutService.performPayout(session.get());

            if (sessionPayout == null) {
                return new SessionPayoutResponse(ResultCodes.PAYOUT_ERROR, null);
            }

            return new SessionPayoutResponse(ResultCodes.OK_RESPONSE, sessionPayout);


        } catch (Exception e) {
            log.error("Exception", e);
        }

        return new SessionPayoutResponse(ResultCodes.GENERAL_ERROR, null);
    }

    @PostMapping(
            value = "/admin/payout/viewBySession", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_VIEW_PAYOUT
    })
    public SessionPayoutResponse payoutBySession(@RequestBody SessionPayoutRequest request) {

        try {

            if (request == null
            ) {
                return new SessionPayoutResponse(ResultCodes.WRONG_REQUEST, null);
            }

            Optional<Session> session = sessionService.findByID(request.getSessionID());

            if (!session.isPresent()) {
                return new SessionPayoutResponse(ResultCodes.WRONG_SESSION, null);
            }

            SessionPayout sessionPayout = sessionPayoutService.findBySession(session.get());

            if (sessionPayout == null) {
                return new SessionPayoutResponse(ResultCodes.PAYOUT_ERROR, null);
            }

            return new SessionPayoutResponse(ResultCodes.OK_RESPONSE, sessionPayout);


        } catch (Exception e) {
            log.error("Exception", e);
        }

        return new SessionPayoutResponse(ResultCodes.GENERAL_ERROR, null);
    }


    @PostMapping(
            value = "/admin/payout/viewBySpecialistAndDate", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_VIEW_PAYOUT
    })
    public SessionPayoutListResponse payoutBySpecialist(@RequestBody SessionPayoutBySpecialistRequest request) {

        try {

            if (request == null
            ) {
                return new SessionPayoutListResponse(ResultCodes.WRONG_REQUEST, null);
            }

            Optional<Specialist> specialistByEmail = specialistService.findSpecialistByEmail(request.getSpecialistEmail());

            if (specialistByEmail.isPresent() && request.getStartDate() != null && request.getEndDate() != null) {
                return new SessionPayoutListResponse(ResultCodes.OK_RESPONSE,
                        sessionPayoutService.findBySessionSpecialistAndDateBetween(specialistByEmail.get(), request.getStartDate(),
                                request.getEndDate()));
            } else if (specialistByEmail.isPresent() && (request.getStartDate() == null && request.getEndDate() == null)) {
                return new SessionPayoutListResponse(ResultCodes.OK_RESPONSE,
                        sessionPayoutService.findBySessionSpecialist(specialistByEmail.get()));
            } else if (!specialistByEmail.isPresent() && request.getStartDate() != null && request.getEndDate() != null) {
                return new SessionPayoutListResponse(ResultCodes.OK_RESPONSE,
                        sessionPayoutService.findByDateBetween(request.getStartDate(),
                                request.getEndDate()));
            } else {
                return new SessionPayoutListResponse(ResultCodes.OK_RESPONSE,
                        sessionPayoutService.findAll());
            }


        } catch (Exception e) {
            log.error("Exception", e);
        }

        return new SessionPayoutListResponse(ResultCodes.GENERAL_ERROR, null);
    }


}



