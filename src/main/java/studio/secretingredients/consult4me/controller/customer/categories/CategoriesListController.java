package studio.secretingredients.consult4me.controller.customer.categories;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import studio.secretingredients.consult4me.CacheProvider;
import studio.secretingredients.consult4me.authorization.CustomerToken;
import studio.secretingredients.consult4me.controller.ResultCodes;
import studio.secretingredients.consult4me.controller.customer.categories.dto.CategoriesList;
import studio.secretingredients.consult4me.controller.customer.categories.dto.CategoriesListResponse;
import studio.secretingredients.consult4me.controller.customer.login.dto.CustomerLogin;
import studio.secretingredients.consult4me.controller.customer.login.dto.CustomerLoginResponse;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.domain.Specialisation;
import studio.secretingredients.consult4me.service.AccountService;
import studio.secretingredients.consult4me.service.CustomerService;
import studio.secretingredients.consult4me.service.SpecialisationService;
import studio.secretingredients.consult4me.util.SecurityUtil;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class CategoriesListController {

    @Autowired
    AccountService accountService;

    @Autowired
    SpecialisationService specialisationService;


    @PostMapping(
            value = "/categories/list", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "SHA256(accountID+privateKey)"
                    , name = "checksum")})
    public CategoriesListResponse login(@RequestBody CategoriesList categoriesList) {

        try {

            if (categoriesList == null || StringUtils.isBlank(categoriesList.getAccountID())
                    || StringUtils.isBlank(categoriesList.getCheckSum())
            ) {
                return new CategoriesListResponse(ResultCodes.WRONG_REQUEST, null);
            }

            Account account = accountService.findAccountByID(Integer.parseInt(categoriesList.getAccountID()));

            if (account == null || !account.isActive()) {
                return new CategoriesListResponse(ResultCodes.WRONG_ACCOUNT, null);
            }


            if (!SecurityUtil.generateKeyFromArray(categoriesList.getAccountID(),
                    account.getPrivateKey()).equalsIgnoreCase(categoriesList.getCheckSum())) {
                return new CategoriesListResponse(ResultCodes.WRONG_CHECKSUM, null);
            }


            return new CategoriesListResponse(ResultCodes.OK_RESPONSE, specialisationService.findAll());

        } catch (Exception e) {
            log.error("Error", e);
            return new CategoriesListResponse(ResultCodes.GENERAL_ERROR, null);
        }
    }
}



