package com.gpesce.challenge.superheroapi.profiler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecTimeMeasurementAspect {

    @Around("@annotation(ExecTimeMeasurement)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long execTime = System.currentTimeMillis() - startTime;
        log.info(joinPoint.toShortString() + " executed in: " + execTime + "ms");

        return proceed;
    }
}