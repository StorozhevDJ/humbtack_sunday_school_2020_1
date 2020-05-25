package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.serverexception.ServerError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Value("${min_password_length}")
    private int minPasswordLength;

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.PASSWORD_EMPTY.getMessage()).addConstraintViolation();
            return false;
        }
        if (s.length() < minPasswordLength) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.PASSWORD_SHORT.getMessage()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
