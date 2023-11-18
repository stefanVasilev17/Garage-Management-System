package com.stefan.security.GarageModule.customAnnotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class RankValidator implements ConstraintValidator<RankValidation, String>
{
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context)
  {
    List<String> list = Arrays.asList("TRAINEE", "NEWBIE", "ADVANCED", "EXPERT");
    return list.contains(value.toUpperCase());
  }
}
