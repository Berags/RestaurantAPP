package edu.unifi.api.security.aop;

import edu.unifi.api.security.Roles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation is used to check if the current user is authorized to access the method.
 * It is retained at runtime and can be applied to methods.
 * By default, it assigns the role of ADMIN.
 *
 * @Retention  RetentionPolicy.RUNTIME
 * @Target     ElementType.METHOD
 * @default    Roles.ADMIN
 * @author Jacopo Beragnoli
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorize {
    Roles role() default Roles.ADMIN;
}
