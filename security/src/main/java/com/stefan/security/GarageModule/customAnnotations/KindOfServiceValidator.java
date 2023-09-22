package com.stefan.security.GarageModule.customAnnotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class KindOfServiceValidator implements ConstraintValidator<KindOfServiceValidation, String>
{
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context)
  {
    List<String> list = Arrays.asList("LIGHT", "ADVANCED", "FULL EXPERIENCE");
    return list.contains(value.toUpperCase());
  }
}
