package studio.secretingredients.consult4me.controller.admin.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import studio.secretingredients.consult4me.controller.BaseTokenRequest;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.domain.Specialist;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerListResponse {

    private String result;

    public List<Customer> customers;
}
