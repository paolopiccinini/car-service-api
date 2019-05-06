package com.advantio.carserviceapi.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FuelTypeValidator.class)
public @interface FuelType {
	
	String message() default "{FuelType.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
