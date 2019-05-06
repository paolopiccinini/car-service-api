package com.advantio.carserviceapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advantio.carserviceapi.domain.Car;
import com.advantio.carserviceapi.service.CarService;

@CrossOrigin
@RestController
@RequestMapping("cars")
public class CarController {

	private CarService carService;

	@Autowired
	public CarController(CarService carService) {
		this.carService = carService;
	}

	@PostMapping
	public Car save(@Valid @RequestBody Car car) {
		return carService.save(car);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Car update(@PathVariable("id") Long id, @Valid @RequestBody Car car) {
		carService.find(id);
		car.setId(id);
		return carService.save(car);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		carService.delete(id);
	}

	@GetMapping
	public List<Car> findAll(@RequestParam(value = "sortColumn", required = false) String sortColumn) {
		return carService.find(sortColumn);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Car find(@PathVariable("id") Long id) {
		return carService.find(id);
	}

}
