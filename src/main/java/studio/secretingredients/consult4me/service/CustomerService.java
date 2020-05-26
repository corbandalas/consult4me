package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

/**
 * Customer service
 *
 * @author corbandalas - created 17.05.2020
 * @since 0.1.0
 */
@Service("customerService")
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findById(email);
    }

    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}