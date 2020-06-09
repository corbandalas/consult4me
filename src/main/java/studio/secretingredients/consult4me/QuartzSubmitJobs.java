package studio.secretingredients.consult4me;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import studio.secretingredients.consult4me.trigger.SessionNotificationJob;

@Configuration
public class QuartzSubmitJobs {
   private static final String CRON_EVERY_FIVE_MINUTES = "0 0/5 * ? * * *";
 
   @Bean(name = "sessionNotification")
   public JobDetailFactoryBean sessionNotification() {
       return QuartzConfig.createJobDetail(SessionNotificationJob.class, "Session notification Job");
   }
 
   @Bean(name = "sessionNotificationTrigger")
   public CronTriggerFactoryBean triggerSessionNotification(@Qualifier("sessionNotification") JobDetail jobDetail) {
       return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_FIVE_MINUTES, "Session notification Trigger");
   }
}