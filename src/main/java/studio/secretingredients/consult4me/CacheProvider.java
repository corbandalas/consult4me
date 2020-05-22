package studio.secretingredients.consult4me;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.engine.control.CompositeCacheManager;
import org.springframework.stereotype.Component;
import studio.secretingredients.consult4me.authorization.admin.AdminUserToken;
import studio.secretingredients.consult4me.authorization.customer.CustomerToken;
import studio.secretingredients.consult4me.authorization.specialist.SpecialistToken;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class CacheProvider {

    CacheAccess<String, AdminUserToken> adminUsertokenCache;
    CacheAccess<String, CustomerToken> customerCache;
    CacheAccess<String, SpecialistToken> specialistCache;
    CacheAccess<String, Object> defaultCache;

    @PostConstruct
    public void init() {

        log.info("///// Loading Cache Provider");

        CompositeCacheManager compositeCacheManager = CompositeCacheManager.getInstance();

        compositeCacheManager.configure();

        adminUsertokenCache = JCS.getInstance("adminUsertokenCache");
        defaultCache = JCS.getInstance("defaultCache");

    }

    public Object getObject(String key) {
        return defaultCache.get(key);
    }

    public AdminUserToken getAdminUserToken(String key) {
        return adminUsertokenCache.get(key);
    }
    public CustomerToken getCustomerToken(String key) {
        return customerCache.get(key);
    }
    public SpecialistToken getSpecialistToken(String key) {
        return specialistCache.get(key);
    }

    public void putObject(String key, Object object) {
        defaultCache.put(key, object);
    }

    public void putAdminUserToken(String key, AdminUserToken token) {
        adminUsertokenCache.put(key, token);
    }

    public void putCustomerToken(String key, CustomerToken token) {
        customerCache.put(key, token);
    }

    public void putSpecialistToken(String key, SpecialistToken token) {
        specialistCache.put(key, token);
    }


}