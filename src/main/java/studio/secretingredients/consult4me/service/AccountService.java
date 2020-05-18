package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Account;
import studio.secretingredients.consult4me.repository.AccountRepository;

import java.util.List;

/**
 * Account service
 *
 * @author corbandalas - created 17.05.2020
 * @since 0.1.0
 */
@Service("accountService")
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public List<Account> findAll() {
        return (List<Account>) accountRepository.findAll();
    }

    public Account findAccountByID(Integer id) {
        return accountRepository.findAccountById(id);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }
}