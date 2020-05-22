package studio.secretingredients.consult4me.authorization.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import studio.secretingredients.consult4me.CacheProvider;

@Component
public class AdminUserAuthorizationBean {

    @Autowired
    CacheProvider cacheProvider;

    public AdminUserToken authorize(String token) {
        return cacheProvider.getAdminUserToken(token);
    }
}