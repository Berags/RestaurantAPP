package edu.unifi.api.security.aop;

import edu.unifi.api.security.Authorize;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AuthorizeAspect {
    @Pointcut("execution(* *(..)) && @annotation(edu.unifi.api.security.Authorize)")
    public void authorizing() {
    }

    @Around("authorizing()")
    public Object authorizeMethod(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        System.out.println("Before " + thisJoinPoint.getSignature());
        Object ret = thisJoinPoint.proceed();
        System.out.println("After " + thisJoinPoint.getSignature());

        return ret;
    }
}
