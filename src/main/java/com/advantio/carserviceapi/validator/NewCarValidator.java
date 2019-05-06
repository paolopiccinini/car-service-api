package com.advantio.carserviceapi.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.advantio.carserviceapi.domain.Car;

public class NewCarValidator implements ConstraintValidator<NewCar, Car> {
	
	@Override
	public boolean isValid(Car car, ConstraintValidatorContext context) {
		if(car.getNewCar() == null) {
			return false;
		}
		if(car.getNewCar()) {
			return car.getMileage() == null && car.getFirstRegistration() == null;
		} else {
			return car.getMileage() != null && car.getFirstRegistration() != null;
		}
	}
}
