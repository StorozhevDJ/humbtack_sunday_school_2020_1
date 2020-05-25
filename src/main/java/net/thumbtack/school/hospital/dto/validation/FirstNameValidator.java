package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.serverexception.ServerError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class FirstNameValidator implements ConstraintValidator<FirstName, String> {

    @Value("${max_name_length}")
    private int maxNameLength;

    @Override
    public void initialize(FirstName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.NAME_EMPTY.getMessage()).addConstraintViolation();
            return false;
        }
        if (s.length() > maxNameLength) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.NAME_LONG.getMessage()).addConstraintViolation();
            return false;
        }
        if (!s.matches("^[-а-яА-ЯёЁ\\s]+$")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.NAME_INVALID.getMessage()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
