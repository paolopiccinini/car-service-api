package com.advantio.carserviceapi.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.advantio.carserviceapi.validator.FuelType;
import com.advantio.carserviceapi.validator.NewCar;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@NewCar
public class Car {
	
	@Id
	@GeneratedValue
	@Column(name = "CAR_ID")
	private Long id;
	
	@NotNull
	private String title;
	
	@NotNull
	@FuelType
	private String fuel;
	
	@NotNull
	private Integer price;
	
	@NotNull
	private Boolean newCar;
	
	private Integer mileage;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate firstRegistration;
	
}
