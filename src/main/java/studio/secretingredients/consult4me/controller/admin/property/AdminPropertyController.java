package studio.secretingredients.consult4me.controller.admin.property;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.authorization.admin.AdminUserAuthorized;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.admin.property.dto.PropertyList;
import studio.secretingredients.consult4me.controller.admin.property.dto.PropertyListResponse;
import studio.secretingredients.consult4me.controller.admin.property.dto.PropertyUpdate;
import studio.secretingredients.consult4me.controller.admin.property.dto.PropertyUpdateResponse;
import studio.secretingredients.consult4me.controller.admin.session.dto.SessionListResponse;
import studio.secretingredients.consult4me.domain.AdminRole;
import studio.secretingredients.consult4me.domain.Property;
import studio.secretingredients.consult4me.service.PropertyService;

@RestController
public class AdminPropertyController {

    @Autowired
    PropertyService propertyService;

    @PostMapping(
            value = "/admin/property/list", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_PROPERTY_LIST
    })
    public PropertyListResponse list(@RequestBody PropertyList request) {

        return new PropertyListResponse(ResultCodes.OK_RESPONSE, propertyService.findAll());
    }

    @PostMapping(
            value = "/admin/property/update", consumes = "application/json", produces = "application/json")
    @AdminUserAuthorized(requiredRoles = {
            AdminRole.ROLE_ADMIN_PROPERTY_UPDATE
    })
    public PropertyUpdateResponse update(@RequestBody PropertyUpdate request) {

        if (request == null
                || StringUtils.isBlank(request.getValue())
                ) {
            return new PropertyUpdateResponse(ResultCodes.WRONG_REQUEST, null);
        }

        Property propertyById = propertyService.findPropertyById(request.getId());

        if (propertyById == null) {
            return new PropertyUpdateResponse(ResultCodes.WRONG_REQUEST, null);
        }

        propertyById.setValue(request.getValue());

        Property save = propertyService.save(propertyById);

        return new PropertyUpdateResponse(ResultCodes.OK_RESPONSE, save);
    }

}



