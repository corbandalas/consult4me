package studio.secretingredients.consult4me.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.Session;
import studio.secretingredients.consult4me.domain.SessionPayout;
import studio.secretingredients.consult4me.repository.SessionRepository;

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
}