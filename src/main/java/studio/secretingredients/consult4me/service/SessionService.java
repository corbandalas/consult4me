package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.*;
import studio.secretingredients.consult4me.repository.SessionRepository;

import java.util.List;

/**
 * Session service
 *
 * @author corbandalas - created 17.05.2020
 * @since 0.1.0
 */
@Service("sessionService")
public class SessionService {

    @Autowired
    SessionRepository sessionRepository;

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public List<Session> findByCustomer(Customer customer) {
        return sessionRepository.findByCustomer(customer);
    }
    public List<Session> findBySpecialist(Specialist specialist) {
        return sessionRepository.findBySpecialist(specialist);
    }
    public List<Session> findByState(SessionState sessionState) {
        return sessionRepository.findBySessionState(sessionState);
    }

}