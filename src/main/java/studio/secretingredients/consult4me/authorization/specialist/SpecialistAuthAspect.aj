package studio.secretingredients.consult4me.authorization.specialist;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
public class SpecialistAuthAspect {
    @Autowired
    SpecialistAuthorizationBean specialistAuthorizationBean;

    @Before("@annotation(studio.secretingredients.consult4me.authorization.specialist.SpecialistAuthorized) && args(request,..)")
    public void before(HttpServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw
                    new RuntimeException("request should be HttpServletRequesttype");
        }

        SpecialistToken authorization = specialistAuthorizationBean.authorize(request.getHeader("Authorization"));

        if (authorization != null) {
            request.setAttribute("userSession", authorization);
        } else {
            throw new RuntimeException("auth error..!!!");
        }

    }

}
