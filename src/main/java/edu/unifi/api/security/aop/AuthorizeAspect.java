package edu.unifi.api.security.aop;

import edu.unifi.api.security.Authorize;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Objects;

@Aspect
public class AuthorizeAspect {
    @Pointcut("execution(* *(..)) && @annotation(authorize)")
    public void callAt(Authorize authorize) {
    }

    @Around("callAt(authorize)")
    public Object around(ProceedingJoinPoint pjp,
                         Authorize authorize) throws Throwable {
        System.out.println(authorize.role());
        if (authorize.role().equals("Admin")) return pjp.proceed();
        return null;
    }
}
