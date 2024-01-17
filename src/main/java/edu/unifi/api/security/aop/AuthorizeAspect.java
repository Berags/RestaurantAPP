package edu.unifi.api.security.aop;

import edu.unifi.Main;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.logging.Logger;

@Aspect
public class AuthorizeAspect {
    static Logger log = Logger.getLogger("SecurityManager");

    @Pointcut("execution(* *(..)) && @annotation(authorize)")
    public void callAt(Authorize authorize) {
    }

    @Around("callAt(authorize)")
    public Object around(ProceedingJoinPoint pjp,
                         Authorize authorize) throws Throwable {
        log.info("Authorizing the method: " + pjp.getSignature() + " with role access " + authorize.role());
        if (authorize.role().equals("Admin")) return pjp.proceed();
        return null;
    }
}
