package studio.secretingredients.consult4me.authorization;

import org.springframework.stereotype.Component;

@Component
public class AuthorizationBean {

    public boolean authorize(String token) {
        // implemnt jwt or any any token based authorization logic
        return true;
    }
}