package edu.unifi.api.dco.aop;

import edu.unifi.api.dco.DatabaseAccess;
import edu.unifi.api.security.aop.Authorize;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.cfg.Configuration;

@Aspect
public class DatabaseConnectionAspect {
    @Pointcut("execution(* *(..)) && @annotation(databaseConnection)")
    public void callAt(DatabaseConnection databaseConnection) {
    }

    @Around("callAt(databaseConnection)")
    public Object around(ProceedingJoinPoint pjp,
                         DatabaseConnection databaseConnection) throws Throwable {
        System.out.println("Connecting to the database from the method: " + pjp.getSignature());
        DatabaseAccess.getInstance().setSessionFactory(new Configuration().configure("hibernate.cfg.xml").buildSessionFactory());
        DatabaseAccess.getInstance().setSession(DatabaseAccess.getInstance().getSessionFactory().openSession());
        Object ret = pjp.proceed();
        DatabaseAccess.getInstance().getSession().close();
        DatabaseAccess.getInstance().getSessionFactory().close();
        return ret;
    }
}
