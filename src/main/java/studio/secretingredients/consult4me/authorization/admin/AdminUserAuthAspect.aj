package studio.secretingredients.consult4me.authorization.admin;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import studio.secretingredients.consult4me.CacheProvider;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.AdminRole;
import studio.secretingredients.consult4me.domain.User;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.UserService;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Aspect
@Configuration
public class AdminUserAuthAspect {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    CacheProvider cacheProvider;

    @Before("@annotation(studio.secretingredients.consult4me.authorization.admin.AdminUserAuthorized) && args(request,..)")
    public void before(JoinPoint joinPoint, BaseTokenRequest request) {

        if (!(request instanceof BaseTokenRequest)) {
            throw
                    new RuntimeException("Request should be BaseTokenRequest");
        }

        AdminUserToken authorization = authorize(request.getToken());

        if (authorization == null) {
            throw new RuntimeException("Authorization Token was not found !!!");
        }

        Account accountByID = accountService.findAccountByID(authorization.getAccount().getId());

        if (accountByID != null && !accountByID.isActive()) {
            throw new RuntimeException("Account #" + accountByID.getId() + " is not active");
        }

        User userByID = userService.findUserByID(authorization.getUser().getEmail());

        if (userByID != null && !userByID.isActive()) {
            throw new RuntimeException("User # " + userByID.getEmail() + " is not active");
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        AdminUserAuthorized myAnnotation = method.getAnnotation(AdminUserAuthorized.class);

        Set<AdminRole> methodRequiredRoles = new HashSet<>(Arrays.asList(myAnnotation.requiredRoles()));

        Set<AdminRole> roles = userByID.getRoles();

        if (!roles.containsAll(methodRequiredRoles)) {
            throw new RuntimeException("User # " + userByID.getEmail() + " is not authorized to call " + method.getName());
        }

        if ((System.currentTimeMillis() - authorization.getAuthorizeDate().getTime()) <= 1000 * 60 * 15) {
            authorization.setAuthorizeDate(new Date());
            cacheProvider.putAdminUserToken(request.getToken(), authorization);
        }
    }

    public AdminUserToken authorize(String token) {
        return cacheProvider.getAdminUserToken(token);
    }

}
