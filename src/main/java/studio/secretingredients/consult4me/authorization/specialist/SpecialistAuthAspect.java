package studio.secretingredients.consult4me.authorization.specialist;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import studio.secretingredients.consult4me.CacheProvider;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.Specialist;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.SpecialistService;

import java.util.Date;
import java.util.Optional;

@Aspect
@Configuration
@Slf4j
public class SpecialistAuthAspect {

    @Autowired
    AccountService accountService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    CacheProvider cacheProvider;

    @Before("@annotation(studio.secretingredients.consult4me.authorization.specialist.SpecialistAuthorized) && args(request,..)")
    public void before(JoinPoint joinPoint, BaseTokenRequest request) {

        log.info("SpecialistAuthAspect is working");

        if (!(request instanceof BaseTokenRequest)) {
            throw
                    new RuntimeException("Request should be BaseTokenRequest");
        }

        log.info("SpecialistAuthAspect is working1");

        SpecialistToken authorization = authorize(request.getToken());

        log.info("SpecialistAuthAspect is working2");

        if (authorization == null) {
            throw new RuntimeException("Authorization Token was not found !!!");
        }

        log.info("SpecialistAuthAspect is working3");

        Account accountByID = accountService.findAccountByID(authorization.getAccount().getId());

        if (accountByID != null && !accountByID.isActive()) {
            throw new RuntimeException("Account #" + accountByID.getId() + " is not active");
        }

        log.info("SpecialistAuthAspect is working4");

        Optional<Specialist> customerByEmail = specialistService.findSpecialistByEmail(authorization.getSpecialist().getEmail());

        if (!customerByEmail.isPresent()) {
            throw new RuntimeException("Specialist # " + authorization.getSpecialist().getEmail() + " is not present");
        }

        log.info("SpecialistAuthAspect is working5");


        if (customerByEmail.get() != null && !customerByEmail.get().isActive()) {
            throw new RuntimeException("Customer # " + authorization.getSpecialist().getEmail() + " is not active");
        }

        if ((System.currentTimeMillis() - authorization.getAuthorizeDate().getTime()) <= 1000 * 60 * 15) {
            authorization.setAuthorizeDate(new Date());
            cacheProvider.putSpecialistToken(request.getToken(), authorization);
        }

    }


    public SpecialistToken authorize(String token) {
        return cacheProvider.getSpecialistToken(token);
    }


}
