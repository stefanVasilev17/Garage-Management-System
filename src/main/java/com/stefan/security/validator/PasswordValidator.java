package com.stefan.security.validator;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    /**
     * Method to check if password is valid or not.
     * @param password - password to check
     */
    public void isValidPassword(String password)
    {
        if (password.length() > 15 || password.length() < 8)
        {
            throw new IllegalArgumentException("Password must be less than 20 and more than 8 characters in length.");
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
        {
            throw new IllegalArgumentException("Password must have at least one uppercase character");
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
        {
            throw new IllegalArgumentException("Password must have at least one lowercase character");
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            throw new IllegalArgumentException("Password must have at least one number");
        }
        String specialChars = "(.*[@,#,$,%].*$)";
        if (!password.matches(specialChars ))
        {
            throw new IllegalArgumentException("Password must have at least one special character among @#$%");
        }
    }
}
