package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.serverexception.ServerError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PatronymicValidator implements ConstraintValidator<Patronymic, String> {

    @Value("${max_name_length}")
    private int maxNameLength;

    @Override
    public void initialize(Patronymic constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return true;
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
