package studio.secretingredients.consult4me.authorization.admin;

import studio.secretingredients.consult4me.domain.AdminRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // can use in method only.
public @interface AdminUserAuthorized {
  AdminRole[] requiredRoles();
}