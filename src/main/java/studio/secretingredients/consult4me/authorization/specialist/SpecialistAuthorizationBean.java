package studio.secretingredients.consult4me.authorization.specialist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import studio.secretingredients.consult4me.CacheProvider;

@Component
public class SpecialistAuthorizationBean {

    @Autowired
    CacheProvider cacheProvider;

    public SpecialistToken authorize(String token) {
        return cacheProvider.getSpecialistToken(token);
    }
}