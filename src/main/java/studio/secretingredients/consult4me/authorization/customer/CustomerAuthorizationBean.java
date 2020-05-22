package studio.secretingredients.consult4me.authorization.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import studio.secretingredients.consult4me.CacheProvider;

@Component
public class CustomerAuthorizationBean {

    @Autowired
    CacheProvider cacheProvider;

    public CustomerToken authorize(String token) {
        return cacheProvider.getCustomerToken(token);
    }
}