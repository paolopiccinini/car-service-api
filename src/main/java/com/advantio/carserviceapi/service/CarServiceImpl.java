package com.advantio.carserviceapi.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.advantio.carserviceapi.domain.Car;
import com.advantio.carserviceapi.exception.CarNotFoundException;
import com.advantio.carserviceapi.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {

	private CarRepository carRepository;
	private static final String DEFAULT_ORDER_BY = "id";

	@Autowired
	public CarServiceImpl(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@Override
	public List<Car> find(String sortColumn) {
		return carRepository.findAll(orderBy(sortColumn));
	}

	@Override
	public Car find(Long id) {
		Optional<Car> carOptional = carRepository.findById(id);
		if(!carOptional.isPresent()) {
			throw new CarNotFoundException("No car with id: " + id);
		}
		return carOptional.get();
	}

	@Override
	@Transactional
	public Car save(Car car) {
		return carRepository.save(car);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		try {
			carRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new CarNotFoundException("No car with id: " + id);
		}
	}

	private Sort orderBy(String sortColumn) {
		return new Sort(Direction.ASC, (sortColumn == null) ? DEFAULT_ORDER_BY : sortColumn);
	}

}
