package studio.secretingredients.consult4me;

import com.bancore.paymentconsole.domain.AdminRole;
import com.bancore.paymentconsole.domain.Customer;
import com.bancore.paymentconsole.domain.Partner;
import com.bancore.paymentconsole.domain.Property;
import com.bancore.paymentconsole.repository.CustomerRepository;
import com.bancore.paymentconsole.repository.PartnerRepository;
import com.bancore.paymentconsole.repository.PropertyRepository;
import com.bancore.paymentconsole.service.CustomerService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SpringBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private PropertyRepository propertyRepository;

    private Logger log = Logger.getLogger(SpringBootstrap.class);

    private static final Type REVIEW_TYPE = new TypeToken<List<Property>>() {
    }.getType();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        loadProperty();


    }

    private void loadProperty() {

        log.info("Start Load Property from properties.json file.");

        Gson gson = new Gson();

        try (JsonReader reader = new JsonReader(new InputStreamReader(new ClassPathResource("/properties.json").getInputStream()))) {

            List<Property> data = gson.fromJson(reader, REVIEW_TYPE);

            for (Property property : data) {

                Property findProperty = propertyRepository.findPropertyByKey(property.getKey());
                if (findProperty == null) {
                    propertyRepository.save(property);
                    log.info("New property detected: ".concat(property.getKey()).concat(" and created."));
                }

            }
        } catch (Exception ex) {
            log.error(ex);
        }

        log.info("End Load Property from properties.json file.");
    }

}