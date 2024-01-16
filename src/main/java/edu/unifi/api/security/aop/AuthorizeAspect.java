package edu.unifi.api.security.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AuthorizeAspect {
    @Pointcut("execution(* *(..)) && @annotation(authorize)")
    public void callAt(Authorize authorize) {
    }

    @Around("callAt(authorize)")
    public Object around(ProceedingJoinPoint pjp,
                         Authorize authorize) throws Throwable {
        System.out.println("Authorizing the method: " + pjp.getSignature() + " with role access " + authorize.role());
        if (authorize.role().equals("Admin")) return pjp.proceed();
        return null;
    }
}
