package edu.unifi.api.security.aop;

import edu.unifi.Main;
import edu.unifi.api.security.CurrentSession;
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
        if (CurrentSession.getInstance().isAuthorized(authorize.role())) return pjp.proceed();
        throw new SecurityException("The current user is not authorized to access the method: " + pjp.getSignature() + ". CurrentRole: " + CurrentSession.getInstance().getRole() + " - required role: " + authorize.role());
    }
}
