package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Customer;
import studio.secretingredients.consult4me.domain.SessionPayout;
import studio.secretingredients.consult4me.repository.SessionPayoutRepository;

/**
 * SessionPayout service
 *
 * @author corbandalas - created 17.05.2020
 * @since 0.1.0
 */
@Service("sessionPayoutService")
public class SessionPayoutService {

    @Autowired
    SessionPayoutRepository sessionPayoutRepository;

    public SessionPayout save(SessionPayout sessionPayout) {
        return sessionPayoutRepository.save(sessionPayout);
    }
}