package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.serverexception.ServerError;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<Date, String> {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    @Override
    public void initialize(Date constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            LocalDate.parse(s, DateTimeFormatter.ofPattern(DATE_FORMAT));
            return true;
        } catch (DateTimeParseException ex) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.BAD_DATE.getMessage()).addConstraintViolation();
            return false;
        }
    }

}
