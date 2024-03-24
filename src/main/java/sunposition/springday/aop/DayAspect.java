package sunposition.springday.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class DayAspect {

    @Around("execution(* sunposition.springday.service.DayService.findAllSunriseSunset(..))")
    public Object aroundFindAllSunriseSunsetAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering findAllSunriseSunset method");
        Object result = joinPoint.proceed();
        log.info("Exiting findAllSunriseSunset method with result: {}", result);
        return result;
    }

    @Around("execution(* sunposition.springday.service.DayService.saveSunriseSunset(..))")
    public Object aroundSaveSunriseSunsetAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering saveSunriseSunset method");
        Object result = joinPoint.proceed();
        log.info("Exiting saveSunriseSunset method with result: {}", result);
        return result;
    }

    @Around("execution(* sunposition.springday.service.DayService.findByLocation(..))")
    public Object aroundFindByLocationAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering findByLocation method");
        Object result = joinPoint.proceed();
        log.info("Exiting findByLocation method with result: {}", result);
        return result;
    }

    @Around("execution(* sunposition.springday.service.DayService.findByCoordinates(..))")
    public Object aroundFindByCoordinatesAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering findByCoordinates method");
        Object result = joinPoint.proceed();
        log.info("Exiting findByCoordinates method with result: {}", result);
        return result;
    }

    @Around("execution(* sunposition.springday.service.DayService.deleteDayByCoordinates(..))")
    public Object aroundDeleteDayByCoordinatesAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering deleteDayByCoordinates method");
        Object result = joinPoint.proceed();
        log.info("Exiting deleteDayByCoordinates method with result: {}", result);
        return result;
    }

    @Around("execution(* sunposition.springday.service.DayService.updateSunriseSunset(..))")
    public Object aroundUpdateSunriseSunsetAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering updateSunriseSunset method");
        Object result = joinPoint.proceed();
        log.info("Exiting updateSunriseSunset method with result: {}", result);
        return result;
    }
}
