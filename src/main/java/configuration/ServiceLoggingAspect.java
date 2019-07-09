package configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ServiceLoggingAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* services.*.*(..))")
    public void logBeforeExecution(JoinPoint joinPoint) {
        logger.info("executing {} with parameters {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(
            pointcut = "execution(* services.*.*(..))",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("returning {} ", result);
    }

    @AfterThrowing(
            pointcut = "execution(* services.*.*(..))",
            throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("throwing exception: ", error);
    }
}