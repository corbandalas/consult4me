package studio.secretingredients.consult4me.controller.admin.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.CacheProvider;
import studio.secretingredients.consult4me.authorization.customer.CustomerAuthorized;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.admin.customer.dto.CustomerSpecialistList;
import studio.secretingredients.consult4me.controller.admin.customer.dto.CustomerListResponse;
import studio.secretingredients.consult4me.controller.admin.customer.dto.SpecialistListResponse;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.CustomerService;
import studio.secretingredients.consult4me.service.SpecialisationService;
import studio.secretingredients.consult4me.service.SpecialistService;

@RestController
@Slf4j
public class CustomerSpecialistController {

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    CacheProvider cacheProvider;

    @Autowired
    SpecialisationService specialisationService;


    @PostMapping(
            value = "/admin/specialist/list", consumes = "application/json", produces = "application/json")
    @CustomerAuthorized
    public SpecialistListResponse specialistList(@RequestBody CustomerSpecialistList request) {

        return new SpecialistListResponse(ResultCodes.OK_RESPONSE, specialistService.findAll());
    }

    @PostMapping(
            value = "/admin/customer/list", consumes = "application/json", produces = "application/json")
    @CustomerAuthorized
    public CustomerListResponse customerList(@RequestBody CustomerSpecialistList request) {

        return new CustomerListResponse(ResultCodes.OK_RESPONSE, customerService.findAll());
    }



}



