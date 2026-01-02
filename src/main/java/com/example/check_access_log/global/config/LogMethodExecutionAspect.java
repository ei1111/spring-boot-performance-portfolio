package com.example.check_access_log.global.config;

import com.example.check_access_log.global.annotation.LogMethodExecution;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogMethodExecutionAspect {

    @Around("@annotation(target)")
    public Object logMethodExecution(ProceedingJoinPoint pjp, LogMethodExecution target) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();

        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = signature.getMethod().getName();
        String classMethod = className + "." + methodName;
        String methodPath = pjp.getTarget().getClass().getName();

        log.info("===== START {} =====", classMethod);
        log.info("Method Path : {}", methodPath);
        log.info("Description : {}", target.description());
        log.info("Arguments   : {}", Arrays.toString(pjp.getArgs()));

        long startTime = System.currentTimeMillis();

        Object result = pjp.proceed();

        long endTime = System.currentTimeMillis();

        log.info("Execution Time : {} ms", endTime - startTime);
        log.info("===== END {} =====", classMethod);

        return result;
    }
}

