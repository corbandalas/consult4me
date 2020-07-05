package studio.secretingredients.consult4me.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.secretingredients.consult4me.domain.*;
import studio.secretingredients.consult4me.integration.api.liqpay.LiqPay;
import studio.secretingredients.consult4me.repository.SessionPayoutRepository;
import studio.secretingredients.consult4me.repository.SessionRepository;
import studio.secretingredients.consult4me.util.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SessionPayout service
 *
 * @author corbandalas - created 17.05.2020
 * @since 0.1.0
 */
@Service("sessionPayoutService")
@Slf4j
public class SessionPayoutService {

    @Autowired
    SessionPayoutRepository sessionPayoutRepository;

    @Autowired
    PropertyService propertyService;

    @Autowired
    SessionRepository sessionRepository;

    public SessionPayout save(SessionPayout sessionPayout) {
        return sessionPayoutRepository.save(sessionPayout);
    }


    public SessionPayout findBySession(Session session) {
        return sessionPayoutRepository.findBySession(session);
    }

    public List<SessionPayout> findBySessionSpecialist(Specialist specialist) {
        return sessionPayoutRepository.findBySessionSpecialist(specialist);
    }
    public List<SessionPayout> findByDateBetween(Date startDate, Date endDate) {
        return sessionPayoutRepository.findByDateBetween(startDate, endDate);
    }

    public List<SessionPayout> findBySessionSpecialistAndDateBetween(Specialist specialist, Date startDate, Date endDate) {
        return sessionPayoutRepository.findBySessionSpecialistAndDateBetween(specialist, startDate, endDate);
    }

    public List<SessionPayout> findAll() {
        return (List<SessionPayout>)sessionPayoutRepository.findAll();
    }

    public SessionPayout performPayout(Session session) {
        if (session.isCustomerConfirmed()) {

            try {

                String orderID = StringUtils.abbreviate(session.getSpecialist().getFirstName() + session.getSpecialist().getLastName(), 4) + System.currentTimeMillis();


                long payoutAmount = session.getPrice();

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "p2pcredit");
                params.put("version", "3");
                params.put("amount", "" + (double) payoutAmount / 100);
                params.put("currency", session.getCurrency());
                params.put("description", "Выплата");
                params.put("order_id", orderID);
                params.put("receiver_card", session.getSpecialist().getPan());
                params.put("receiver_last_name", session.getSpecialist().getLastName());
                params.put("receiver_first_name", session.getSpecialist().getFirstName());

                LiqPay liqpay = new LiqPay(propertyService.findPropertyByKey("studio.secretingredients.liqpay.public.key").getValue(),
                        propertyService.findPropertyByKey("studio.secretingredients.liqpay.private.key").getValue());
                Map<String, Object> res = liqpay.api("request", params);

                Util.printMap(res);
                String status = (String) res.get("status");

                if (status.equalsIgnoreCase("success") || status.equalsIgnoreCase("sandbox")) {

                    session.setSessionState(SessionState.COMPLETED);

                    sessionRepository.save(session);

                    SessionPayout sessionPayout = new SessionPayout();

                    sessionPayout.setDate(new Date());
                    sessionPayout.setOrderID(orderID);
                    sessionPayout.setPayoutAmount(payoutAmount);
                    sessionPayout.setPayoutCurrency(session.getCurrency());
                    sessionPayout.setSession(session);

                    sessionPayoutRepository.save(sessionPayout);

                    return sessionPayout;

                }
            } catch (Exception e) {
                log.error("Error while performing payout logic", e);
            }


        }

        return null;
    }
}