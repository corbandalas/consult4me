package studio.secretingredients.consult4me.controller.admin.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.authorization.admin.AdminUserAuthorized;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.admin.property.dto.PropertyList;
import studio.secretingredients.consult4me.controller.admin.property.dto.PropertyListResponse;
import studio.secretingredients.consult4me.domain.AdminRole;
import studio.secretingredients.consult4me.service.PropertyService;

@RestController
public class AdminPropertyController {

    @Autowired
    PropertyService propertyService;

    @PostMapping(
            value = "/admin/listProperty", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_PROPERTY_LIST
    })
    public PropertyListResponse list(@RequestBody PropertyList request) {

        return new PropertyListResponse(ResultCodes.OK_RESPONSE, propertyService.findAll());
    }

}



