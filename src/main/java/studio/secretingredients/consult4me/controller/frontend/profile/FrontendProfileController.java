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
import studio.secretingredients.consult4me.authorization.customer.CustomerAuthorized;
import studio.secretingredients.consult4me.authorization.customer.CustomerToken;
import studio.secretingredients.consult4me.authorization.specialist.SpecialistAuthorized;
import studio.secretingredients.consult4me.authorization.specialist.SpecialistToken;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.admin.customer.dto.AdminSpecialistFindTimeResponse;
import studio.secretingredients.consult4me.controller.admin.customer.dto.AdminSpecialistTimeResponse;
import studio.secretingredients.consult4me.controller.admin.session.dto.SessionListResponse;
import studio.secretingredients.consult4me.controller.frontend.profile.dto.*;
import studio.secretingredients.consult4me.controller.frontend.register.dto.SpecialistSpecialisation;
import studio.secretingredients.consult4me.domain.*;
import studio.secretingredients.consult4me.integration.api.liqpay.LiqPay;
import studio.secretingredients.consult4me.service.*;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.*;

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

    @Autowired
    SessionService sessionService;

    @Autowired
    PropertyService propertyService;


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

        Specialist specialist1 = specialistService.findSpecialistByEmail(specialist.getEmail()).get();

        return new SpecialistGetResponse(ResultCodes.OK_RESPONSE, specialist1, specialist1.getSpecialisations());
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
                    || request.getPriceHour() <  0
                    || StringUtils.isBlank(request.getCurrency())
                    || StringUtils.isBlank(request.getSocialProfile())
                    || StringUtils.isBlank(request.getFirstName())
                    || StringUtils.isBlank(request.getLastName())
                    || StringUtils.isBlank(request.getPhone())
                    ) {
                return new SpecialistGetResponse(ResultCodes.WRONG_REQUEST, null, null);
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
            specialist.setLastName(request.getLastName());
            specialist.setFirstName(request.getFirstName());
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
            value = "/frontend/categories/list", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "SHA256(accountID+privateKey)"
                    , name = "checksum")})
    public CategoriesListResponse categoriesList(@RequestBody CategoriesList categoriesList) {

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
    @SpecialistAuthorized
    public AdminSpecialistFindTimeResponse findSpecialistTime(@RequestBody FrontendSpecialistFindTime request) {

        Specialist specialist = cacheProvider.getSpecialistToken(request.getToken()).getSpecialist();

        List<SpecialistTime> specialistTime = null;

        if (request.getStartSearchPeriod() != null && request.getEndSearchPeriod() != null ) {
            specialistTime = specialistTimeService.findStartDateAfterStartAndEndDateBeforeEndBySpecialist(request.getStartSearchPeriod(),
                    request.getEndSearchPeriod(), specialist);
        } else {
            specialistTime = specialistTimeService.findSpecialistTime(specialist);
        }

        return new AdminSpecialistFindTimeResponse(ResultCodes.OK_RESPONSE, specialistTime);
    }


    @PostMapping(
            value = "/frontend/specialist/updateTime", consumes = "application/json", produces = "application/json")
    @SpecialistAuthorized
    public AdminSpecialistTimeResponse updateSpecialistTime(@RequestBody FrontendSpecialistUpdateTime request) {

        SpecialistTime specialistTime = specialistTimeService.findById(request.getId());

        specialistTime.setStartDate(request.getStartDate());
        specialistTime.setEndDate(request.getEndDate());
        specialistTime.setFree(request.isFree());

        SpecialistTime save = specialistTimeService.save(specialistTime);

        return new AdminSpecialistTimeResponse(ResultCodes.OK_RESPONSE, save);
    }

    @PostMapping(
            value = "/frontend/customer/getSpecialistTime", consumes = "application/json", produces = "application/json")
    @CustomerAuthorized
    public FrontendSpecialistTimeListResponse getSpecialistTimeList(@RequestBody FrontendSpecialistTimeList request) {


        Optional<Specialist> specialistByEmail = specialistService.findSpecialistByEmail(request.getSpecialistEmail());

        Specialist specialist = specialistByEmail.get();

        List<SpecialistTime> specialistTime = specialistTimeService.findSpecialistTime(specialist);


        return new FrontendSpecialistTimeListResponse(ResultCodes.OK_RESPONSE, specialistTime);
    }


    @PostMapping(
            value = "/frontend/customer/sessions", consumes = "application/json", produces = "application/json")
    @CustomerAuthorized
    public SessionListResponse sessionListByCustomer(@RequestBody SessionByCustomerList request) {

        try {

            Customer customer = cacheProvider.getCustomerToken(request.getToken()).getCustomer();

            return new SessionListResponse(ResultCodes.OK_RESPONSE, sessionService.findByCustomer(customer));

        } catch (Exception e) {
            log.error("Exception", e);
        }

        return new SessionListResponse(ResultCodes.GENERAL_ERROR, null);
    }

    @PostMapping(
            value = "/frontend/customer/confirmSession", consumes = "application/json", produces = "application/json")
    @CustomerAuthorized
    public SessionConfirmResponse sessionConfirm(@RequestBody SessionConfirm request) {

        try {

            Optional<Session> session = sessionService.findByID(request.getSessionID());

            if (!session.isPresent()) {
                return new SessionConfirmResponse(ResultCodes.WRONG_SESSION, null);
            }

            if (session.get().getSessionState().equals(SessionState.PAYED) && !session.get().isCustomerConfirmed()) {
                Session session1 = session.get();
                session1.setCustomerConfirmed(true);
                Session save = sessionService.save(session1);
                return new SessionConfirmResponse(ResultCodes.OK_RESPONSE, save);
            }

        } catch (Exception e) {
            log.error("Exception", e);
        }

        return new SessionConfirmResponse(ResultCodes.GENERAL_ERROR, null);
    }

    @PostMapping(
            value = "/frontend/customer/initSession", consumes = "application/json", produces = "application/json")
    @CustomerAuthorized
    public FrontendSpecialistInitSessionResponse initSession(@RequestBody FrontendSpecialistInitSession request) {


        if (request == null
                || StringUtils.isBlank(request.getSpecialistEmail())
                || StringUtils.isBlank(request.getSuccessURL())
                || request.getSpecialistTimeID() <= 0
                ) {
            return new FrontendSpecialistInitSessionResponse(ResultCodes.WRONG_REQUEST, null, null);
        }

        SpecialistTime specialistTime = specialistTimeService.findById(request.getSpecialistTimeID());

        if (!specialistTime.isFree()) {
            return new FrontendSpecialistInitSessionResponse(ResultCodes.SPECIALIST_TIME_RESERVED, null, null);
        }

        Specialist specialist = specialistService.findSpecialistByEmail(request.getSpecialistEmail()).get();


        Session session = new Session();
        session.setCustomer(cacheProvider.getCustomerToken(request.getToken()).getCustomer());

        session.setCurrency(specialist.getCurrency());
        session.setPrice(specialist.getPriceHour());
        session.setOrderedDate(new Date());
        session.setSpecialist(specialist);
        session.setSpecialistTime(specialistTime);
        session.setSessionState(SessionState.ORDERED);
        session.setOrderID("" + System.currentTimeMillis());
        session.setNotified(false);

        float feePrice = Float.parseFloat(propertyService.findPropertyByKey("studio.secretingredients.fee.percent").getValue()) * specialist.getPriceHour();


        long fee = (long) (feePrice * 100);

        session.setFee(fee);
        session.setTotalPrice(specialist.getPriceHour() + fee);

        specialistTime.setFree(false);

        Session save = sessionService.save(session);

        specialistTimeService.save(specialistTime);

        HashMap<String, String> params = new HashMap<>();
        params.put("action", "pay");
        params.put("amount", "" + (float) ((specialist.getPriceHour() + fee) / 100));
        params.put("currency", specialist.getCurrency());
        params.put("description", "Session pay");
        params.put("order_id", session.getOrderID());
        params.put("version", "3");
        params.put("server_url", propertyService.findPropertyByKey("studio.secretingredients.liqpay.callback.url").getValue());
        params.put("result_url", request.getSuccessURL());

        cacheProvider.putSession("liqpay" + session.getOrderID(), save);

        LiqPay liqpay = new LiqPay(propertyService.findPropertyByKey("studio.secretingredients.liqpay.public.key").getValue(),
                propertyService.findPropertyByKey("studio.secretingredients.liqpay.private.key").getValue());

        liqpay.setCnbSandbox(Boolean.parseBoolean(propertyService.findPropertyByKey("studio.secretingredients.liqpay.sandbox").getValue()));

        String html = liqpay.cnb_form(params);

        return new FrontendSpecialistInitSessionResponse(ResultCodes.OK_RESPONSE, save, html);
    }

    @PostMapping(
            value = "/frontend/customer/liqpay/callback", consumes = "application/json", produces = "application/json")
    public FrontendSpecialistInitSessionResponse callback(@RequestBody FrontendLiqpayCallback request) {


        String publicKey = propertyService.findPropertyByKey("studio.secretingredients.liqpay.public.key").getValue();

        String privateKey = propertyService.findPropertyByKey("studio.secretingredients.liqpay.private.key").getValue();

        LiqPay liqpay = new LiqPay(publicKey, privateKey);

        liqpay.setCnbSandbox(Boolean.parseBoolean(propertyService.findPropertyByKey("studio.secretingredients.liqpay.sandbox").getValue()));

        String sign = liqpay.str_to_sign(
                privateKey +
                        request.getData() +
                        privateKey);

        if (!sign.equalsIgnoreCase(request.getSignature())) {
            log.error("LiqPay callback error. Signature mismastch" );
            return null;
        }


        log.info("Liqpay decoded response: " + SecurityUtil.decodeString(request.getData()));

        return null;
    }

}



