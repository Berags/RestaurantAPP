package edu.unifi.model.util.security.aop;

import edu.unifi.model.util.security.CurrentSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This aspect is used for authorization purposes.
 * It logs the authorization process and throws a SecurityException if the user is not authorized.
 *
 * @author Jacopo Beragnoli
 */
@Aspect
public class AuthorizeAspect {
    // Logger instance for logging security related information.
    static Logger log = LoggerFactory.getLogger(AuthorizeAspect.class);

    /**
     * This pointcut is triggered at the execution of any method annotated with @Authorize.
     * It captures the @Authorize annotation as a parameter.
     *
     * @param authorize the authorize annotation
     * @author Jacopo Beragnoli
     */
    @Pointcut("execution(* *(..)) && @annotation(authorize)")
    public void callAt(Authorize authorize) {
        // This method doesn't need to do anything. It's just a placeholder for the pointcut.
    }

    /**
     * This advice is executed around the pointcut 'callAt'.
     * It checks if the current user is authorized to access the method.
     * If authorized, it proceeds with the method execution.
     * Otherwise, it throws a SecurityException.
     *
     * @param pjp       the proceeding join point
     * @param authorize the authorize annotation
     * @return the result of the method execution if the user is authorized
     * @throws Throwable if the user is not authorized
     * @author Jacopo Beragnoli
     */
    @Around(value = "callAt(authorize)", argNames = "pjp,authorize")
    public Object around(ProceedingJoinPoint pjp,
                         Authorize authorize) throws Throwable {
        // Log the authorization attempt.
        log.info("Authorizing the method: " + pjp.getSignature() + " with role access " + authorize.role());

        // Check if the current user is authorized.
        if (CurrentSession.getInstance().isAuthorized(authorize.role())) {
            // If authorized, proceed with the method execution.
            return pjp.proceed();
        }

        // If not authorized, throw a SecurityException.
        throw new SecurityException("The current user is not authorized to access the method: " + pjp.getSignature() + ". CurrentRole: " + CurrentSession.getInstance().getRole() + " - required role: " + authorize.role());
    }
}