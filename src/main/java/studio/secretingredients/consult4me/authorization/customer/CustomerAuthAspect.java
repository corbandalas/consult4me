package studio.secretingredients.consult4me.authorization.customer;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import studio.secretingredients.consult4me.CacheProvider;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.CustomerService;

import java.util.Date;
import java.util.Optional;

@Aspect
@Configuration
public class CustomerAuthAspect {

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CacheProvider cacheProvider;

    @Before("@annotation(studio.secretingredients.consult4me.authorization.customer.CustomerAuthorized) && args(request,..)")
    public void before(JoinPoint joinPoint, BaseTokenRequest request) {

        if (!(request instanceof BaseTokenRequest)) {
            throw
                    new RuntimeException("Request should be BaseTokenRequest");
        }

        CustomerToken authorization = authorize(request.getToken());

        if (authorization == null) {
            throw new RuntimeException("Authorization Token was not found !!!");
        }

        Account accountByID = accountService.findAccountByID(authorization.getAccount().getId());

        if (accountByID != null && !accountByID.isActive()) {
            throw new RuntimeException("Account #" + accountByID.getId() + " is not active");
        }

        Optional<Customer> customerByEmail = customerService.findCustomerByEmail(authorization.getCustomer().getEmail());

        if (!customerByEmail.isPresent()) {
            throw new RuntimeException("Customer # " + authorization.getCustomer().getEmail() + " is not present");
        }


        if (customerByEmail.get() != null && !customerByEmail.get().isActive()) {
            throw new RuntimeException("Customer # " + authorization.getCustomer().getEmail() + " is not active");
        }

        if ((System.currentTimeMillis() - authorization.getAuthorizeDate().getTime()) <= 1000 * 60 * 15) {
            authorization.setAuthorizeDate(new Date());
            cacheProvider.putCustomerToken(request.getToken(), authorization);
        }

    }

    public CustomerToken authorize(String token) {
        return cacheProvider.getCustomerToken(token);
    }

}
