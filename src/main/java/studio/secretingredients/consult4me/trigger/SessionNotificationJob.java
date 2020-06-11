package studio.secretingredients.consult4me.trigger;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import studio.secretingredients.consult4me.domain.Session;
import studio.secretingredients.consult4me.domain.SessionState;
import studio.secretingredients.consult4me.domain.SpecialistTime;
import studio.secretingredients.consult4me.mail.EmailSender;
import studio.secretingredients.consult4me.repository.SessionRepository;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@DisallowConcurrentExecution
public class SessionNotificationJob implements Job {

   @Autowired
   SessionRepository sessionRepository;

   @Autowired
   EmailSender emailSender;
 
   @Override
   public void execute(JobExecutionContext context) {
       log.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());

       Date currentDate = new Date();

       Date date = DateUtils.addHours(currentDate, 24);

       List<Session> sessions = sessionRepository.findBySpecialistTimeStartDateBetweenAndSessionState(currentDate, date, SessionState.PAYED);

       for (Session session: sessions) {

           try {

               if (!session.isNotified()) {
                   emailSender.sendCustomerSessionNotification(session.getCustomer().getEmail(),
                           session.getCustomer().getFirstName() + " " + session.getCustomer().getLastName(),
                           session.getSpecialistTime().getStartDate(),
                           session.getSpecialist().getFirstName() + " " + session.getSpecialist().getLastName());

                   emailSender.sendSpecialistSessionNotification(session.getSpecialist().getEmail(),
                           session.getSpecialist().getFirstName() + " " + session.getSpecialist().getLastName(),
                           session.getSpecialistTime().getStartDate(),
                           session.getCustomer().getFirstName() + " " + session.getCustomer().getLastName()
                   );

                   session.setNotified(true);

                   sessionRepository.save(session);
               }



           } catch (Exception e) {
               log.error("Error while sending email", e);
           }
       }

       log.info("Job ** {} ** completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(), context.getNextFireTime());
   }
}