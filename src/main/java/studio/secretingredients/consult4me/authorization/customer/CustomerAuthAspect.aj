package studio.secretingredients.consult4me.authorization.customer;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
public class CustomerAuthAspect {

    @Autowired
    CustomerAuthorizationBean customerAuthorizationBean;

    @Before("@annotation(studio.secretingredients.consult4me.authorization.customer.CustomerAuthorized) && args(request,..)")
    public void before(HttpServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw
                    new RuntimeException("request should be HttpServletRequesttype");
        }

        CustomerToken authorization = customerAuthorizationBean.authorize(request.getHeader("Authorization"));

        if (authorization != null) {
            request.setAttribute("userSession", authorization);
        } else {
            throw new RuntimeException("auth error..!!!");
        }

    }

}
