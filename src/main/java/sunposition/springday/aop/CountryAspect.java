package sunposition.springday.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class CountryAspect {

    @Around("execution(* "
            + "sunposition.springday.service.CountryService.findAll(..))")
    public Object aroundFindAllAdvice(
            final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering findAll method");
        Object result = joinPoint.proceed();
        log.info("Exiting findAll method with result: {}", result);
        return result;
    }

    @Around("execution(* "
            + "sunposition.springday.service.CountryService.saveCountry(..))")
    public Object aroundSaveCountryAdvice(
            final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering saveCountry method");
        Object result = joinPoint.proceed();
        log.info("Exiting saveCountry method with result: {}", result);
        return result;
    }

    @Around("execution(* "
            + "sunposition.springday.service.CountryService."
            + "findByNameCountry(..))")
    public Object aroundFindByNameCountryAdvice(
            final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering findByNameCountry method");
        Object result = joinPoint.proceed();
        log.info("Exiting findByNameCountry method with result: {}", result);
        return result;
    }

    @Around("execution(* "
            + "sunposition.springday.service.CountryService."
            + "deleteCountryById(..))")
    public Object aroundDeleteCountryByIdAdvice(
            final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering deleteCountryById method");
        Object result = joinPoint.proceed();
        log.info("Exiting deleteCountryById method with result: {}", result);
        return result;
    }

    @Around("execution(* "
            + "sunposition.springday.service.CountryService."
            + "updateCountryByName(..))")
    public Object aroundUpdateCountryByNameAdvice(
            final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering updateCountryByName method");
        Object result = joinPoint.proceed();
        log.info("Exiting updateCountryByName method with result: {}", result);
        return result;
    }

    @Around("execution(* "
            + "sunposition.springday.service.CountryService."
            + "findByCountryNameAndWeatherConditions(..))")
    public Object aroundFindByCountryNameAndWeatherConditionsAdvice(
            final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering findByCountryNameAndWeatherConditions method");
        Object result = joinPoint.proceed();
        log.info("Exiting findByCountryNameAndWeatherConditions method"
                + " with result: {}", result);
        return result;
    }
}
