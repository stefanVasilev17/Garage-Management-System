package com.stefan.security.GarageModule.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler
{
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<String> handleException(Exception exception, Model model)
  {
    model.addAttribute("message", exception.getMessage());
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
