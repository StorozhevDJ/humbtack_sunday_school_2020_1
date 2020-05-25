package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.serverexception.ServerError;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeValidator implements ConstraintValidator<Time, String> {

    private static final String TIME_FORMAT = "HH:mm";

    @Override
    public void initialize(Time constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            LocalTime.parse(s, DateTimeFormatter.ofPattern(TIME_FORMAT));
            return true;
        } catch (DateTimeParseException ex) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.BAD_TIME.getMessage()).addConstraintViolation();
            return false;
        }
    }
}
