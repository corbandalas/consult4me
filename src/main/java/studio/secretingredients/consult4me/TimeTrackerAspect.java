package studio.secretingredients.consult4me;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class TimeTrackerAspect {

    @Around("@annotation(TimeTracker)")
    public Object around(ProceedingJoinPoint pJoinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object obj = pJoinPoint.proceed();
        log.info("Total time in execution of method: " + pJoinPoint.getSignature().getName() + " is :" + (System.currentTimeMillis() - startTime) + " ms");
        return obj;
    }
}