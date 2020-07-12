package com.hust.hui.quicksilver.concurrent.reflect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by yihui on 2017/11/10.
 */
@Aspect
public class CheckAspect {

    @Before("@annotation(checkDot)")
    public void process(JoinPoint joinPoint, CheckDot checkDot) throws IllegalAccessException, InstantiationException, NoSuchMethodException {
        ICheckRule rule = checkDot.check().newInstance();

        if(rule.check(joinPoint.getArgs())) {
            throw new IllegalStateException("check argument error!");
        }
    }
}
