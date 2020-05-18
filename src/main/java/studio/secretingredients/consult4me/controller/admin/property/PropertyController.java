package studio.secretingredients.consult4me.controller.admin.property;

import io.netty.handler.codec.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.authorization.Authorized;
import studio.secretingredients.consult4me.domain.Property;
import studio.secretingredients.consult4me.service.PropertyService;

import java.util.List;

@RestController
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @PostMapping(
            value = "/listProperty", produces = "application/json")
    @Authorized
    public List<Property> list(@RequestHeader HttpHeaders headers) {

        return propertyService.findAll();
    }
}



