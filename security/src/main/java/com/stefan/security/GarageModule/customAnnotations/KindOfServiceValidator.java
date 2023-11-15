package com.stefan.security.GarageModule.customAnnotations;

import com.stefan.security.GarageModule.data.entity.KindOfServices;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class KindOfServiceValidator implements ConstraintValidator<KindOfServiceValidation, KindOfServices>
{
  @Override
  public boolean isValid(KindOfServices value, ConstraintValidatorContext context)
  {
    List<String> list = Arrays.asList("LIGHT", "ADVANCED", "FULL_EXPERIENCE");
    return list.contains(value);
  }
}
