package net.thumbtack.school.hospital.dto.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Value("${min_password_length}")
    private int minPasswordLength;

    //private String message;

    @Override
    public void initialize(Password constraintAnnotation) {
        //message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            // REVU можно так, да, но разбрасывать строки по коду - не лучшее решение
            // лучше в initialize вытащить нужные message из ServerError и тут их употребить
            // можно в них %s сделать и тогда тут String.format
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password is empty!").addConstraintViolation();
            return false;
        }
        if (s.length() < minPasswordLength) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password " + s + " is too short!").addConstraintViolation();
            return false;
        }
        return true;
    }
}
