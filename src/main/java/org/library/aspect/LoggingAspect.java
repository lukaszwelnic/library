package org.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Profile("logging")
public class LoggingAspect {

    @Before("execution(* org.library.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Calling: " + joinPoint.getSignature().toShortString());
        System.out.println("Args: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* org.library.service.*.*(..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        System.out.println("Returned from: " + joinPoint.getSignature().toShortString());
        System.out.println("Result: " + result);
    }
}
