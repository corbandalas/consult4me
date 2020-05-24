package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.domain.User;
import studio.secretingredients.consult4me.repository.AccountRepository;
import studio.secretingredients.consult4me.repository.UserRepository;

import java.util.List;

/**
 * User service
 *
 * @author corbandalas - created 18.05.2020
 * @since 0.1.0
 */
@Service("userService")
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserByID(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
}