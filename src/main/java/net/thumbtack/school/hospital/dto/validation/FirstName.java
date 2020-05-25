package net.thumbtack.school.hospital.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstNameValidator.class)
public @interface FirstName {
    String message() default "Incorrect name!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
