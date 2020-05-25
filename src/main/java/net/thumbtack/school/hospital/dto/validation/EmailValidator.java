package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.serverexception.ServerError;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(s)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.EMAIL_INVALID.getMessage()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
