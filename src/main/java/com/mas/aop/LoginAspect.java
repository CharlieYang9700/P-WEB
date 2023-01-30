package com.mas.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoginAspect {

    @Pointcut(value = "@annotation(com.mas.annotation.Login)")
    public void exec(){

    }

    @Around(value = "@annotation(com.mas.annotation.Login)")
    public void loginHandle(ProceedingJoinPoint point){

    }
}
