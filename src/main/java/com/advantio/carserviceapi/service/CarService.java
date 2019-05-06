package com.advantio.carserviceapi.service;

import java.util.List;

import com.advantio.carserviceapi.domain.Car;

public interface CarService {
	
	List<Car> find(String sortColumn);
	Car find(Long id);
	Car save(Car car);
	void delete(Long id);

}
