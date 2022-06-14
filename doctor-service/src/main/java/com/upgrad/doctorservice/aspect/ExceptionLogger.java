package com.upgrad.doctorservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Configuration
public class ExceptionLogger {
    @AfterThrowing(pointcut = "execution(* com.upgrad.doctorservice..*(..))", throwing = "ex")
    public void logAfterThrowingAllMethods(Exception ex) {
        System.out.println(ex);
    }
}
