package studio.secretingredients.consult4me;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import studio.secretingredients.consult4me.trigger.SessionEndedNotificationJob;
import studio.secretingredients.consult4me.trigger.SessionNotificationJob;

@Configuration
public class QuartzSubmitJobs {
    private static final String CRON_EVERY_HOUR = "0 0 0/1 ? * * *";

    @Bean(name = "sessionNotification")
    public JobDetailFactoryBean sessionNotification() {
        return QuartzConfig.createJobDetail(SessionNotificationJob.class, "Session notification Job");
    }

    @Bean(name = "sessionNotificationTrigger")
    public CronTriggerFactoryBean triggerSessionNotification(@Qualifier("sessionNotification") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_HOUR, "Session notification Trigger");
    }

    @Bean(name = "sessionEndedNotification")
    public JobDetailFactoryBean sessionEndedNotification() {
        return QuartzConfig.createJobDetail(SessionEndedNotificationJob.class, "Session ended notification Job");
    }

    @Bean(name = "sessionEndedNotificationTrigger")
    public CronTriggerFactoryBean triggerSessionEndedNotification(@Qualifier("sessionEndedNotification") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_HOUR, "Session ended notification Trigger");
    }
}