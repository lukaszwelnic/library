package org.library.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class CachingAspect {

    private final Map<String, Object> cache = new HashMap<>();

    @Around("execution(* org.library.service.*.*(..))")
    public Object cacheMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        if (joinPoint.getArgs().length == 0) return joinPoint.proceed();

        String key = joinPoint.getSignature() + Arrays.toString(joinPoint.getArgs());
        if (cache.containsKey(key)) {
            System.out.println("From Cache: " + key);
            return cache.get(key);
        }

        Object result = joinPoint.proceed();
        if (result != null) {
            cache.put(key, result);
            System.out.println("Cached result for: " + key);
        }

        return result;
    }
}
