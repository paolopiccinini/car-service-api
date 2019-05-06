package com.advantio.carserviceapi.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class FuelTypeValidator implements ConstraintValidator<FuelType, String>{
	
	
	private List<String> fuelTypes;
	
	@Autowired
	public FuelTypeValidator(@Value("#{'${carservice.fuel.types}'.split(',')}") List<String> fuelTypes) {
		this.fuelTypes = fuelTypes;
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return fuelTypes.contains(value);
	}

}
