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


        List<Session> sessions = sessionRepository.findBySpecialistTimeEndDateBeforeAndSessionState(new Date(), SessionState.PAYED);

        for (Session session : sessions) {

            try {

                if (session.isCustomerConfirmed()) {

                   sessionPayoutService.performPayout(session);


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