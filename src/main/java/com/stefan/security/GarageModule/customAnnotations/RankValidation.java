package com.stefan.security.GarageModule.customAnnotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RankValidator.class)
public @interface RankValidation
{
  //error message
  String message() default "Invalid kind of service: must be TRAINEE, NEWBIE, ADVANCED, EXPERT";

  //represents group of constraints
  Class<?>[] groups() default {};

  //represents additional information about annotation
  Class<? extends Payload>[] payload() default {};
}
