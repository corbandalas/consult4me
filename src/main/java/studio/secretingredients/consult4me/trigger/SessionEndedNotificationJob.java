package studio.secretingredients.consult4me.trigger;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import studio.secretingredients.consult4me.domain.Session;
import studio.secretingredients.consult4me.domain.SessionPayout;
import studio.secretingredients.consult4me.domain.SessionState;
import studio.secretingredients.consult4me.integration.api.liqpay.LiqPay;
import studio.secretingredients.consult4me.mail.EmailSender;
import studio.secretingredients.consult4me.repository.SessionRepository;
import studio.secretingredients.consult4me.service.PropertyService;
import studio.secretingredients.consult4me.service.SessionPayoutService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@DisallowConcurrentExecution
public class SessionEndedNotificationJob implements Job {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    EmailSender emailSender;

    @Autowired
    PropertyService propertyService;

    @Autowired
    SessionPayoutService sessionPayoutService;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());


        List<Session> sessions = sessionRepository.findBySpecialistTimeStartDateAfterAndSessionState(new Date(), SessionState.PAYED);

        for (Session session : sessions) {

            try {

                if (session.isCustomerConfirmed()) {

                    try {

                        String orderID = StringUtils.abbreviate(session.getSpecialist().getFirstName(), 3) + System.currentTimeMillis();


                        long payoutAmount = session.getPrice();

                        HashMap<String, String> params = new HashMap<>();
                        params.put("action", "p2pcredit");
                        params.put("version", "3");
                        params.put("amount", "" + (double)payoutAmount / 100);
                        params.put("currency", session.getCurrency());
                        params.put("description", "Выплата");
                        params.put("order_id", orderID);
                        params.put("receiver_card", session.getSpecialist().getPan());
                        params.put("receiver_last_name", session.getSpecialist().getLastName());
                        params.put("receiver_first_name", session.getSpecialist().getFirstName());

                        LiqPay liqpay = new LiqPay(propertyService.findPropertyByKey("studio.secretingredients.liqpay.public.key").getValue(),
                                propertyService.findPropertyByKey("studio.secretingredients.liqpay.private.key").getValue());
                        Map<String, Object> res = liqpay.api("request", params);

                        String status = (String)res.get("status");

                        if (status.equalsIgnoreCase("success")) {

                            session.setSessionState(SessionState.COMPLETED);

                            sessionRepository.save(session);

                            SessionPayout sessionPayout = new SessionPayout();

                            sessionPayout.setDate(new Date());
                            sessionPayout.setOrderID(orderID);
                            sessionPayout.setPayoutAmount(payoutAmount);
                            sessionPayout.setPayoutCurrency(session.getCurrency());
                            sessionPayout.setSession(session);

                            sessionPayoutService.save(sessionPayout);

                        }
                    } catch (Exception e) {
                        log.error("Error while performing payout logic", e);
                    }


                } else if (!session.isCustomerConfirmed()) {

                    String url = propertyService.findPropertyByKey("studio.secretingredients.url.session.notification.confirm").getValue();

                    emailSender.sendCustomerConfirmSessionNotification(session.getCustomer().getEmail(),
                           session.getCustomer().getFirstName() + " " + session.getCustomer().getLastName(),
                           session.getSpecialistTime().getStartDate(),
                           session.getSpecialist().getFirstName() + " " + session.getSpecialist().getLastName(), url);
                }

            } catch (Exception e) {
                log.error("Error while performing payout logic", e);
            }
        }

        log.info("Job ** {} ** completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(), context.getNextFireTime());
    }
}