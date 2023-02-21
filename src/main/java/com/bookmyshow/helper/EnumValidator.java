package com.bookmyshow.helper;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "Value cannot be null")
@ReportAsSingleViolation
public @interface EnumValidator {

	Class<? extends Enum<?>> enumClass();
	String message() default "must be any of enum {enum}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}