package com.advantio.carserviceapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.advantio.carserviceapi.domain.Car;
import com.advantio.carserviceapi.repository.CarRepository;

public class CarServiceImplTest {
	
	private CarServiceImpl carServiceImpl;
	
	@Mock
	private CarRepository carRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		carServiceImpl = new CarServiceImpl(carRepository);
	}
	
	@Test
	public void findByIdTest() {
		Car carTest = new Car();
		carTest.setId(1L);
		carTest.setFuel("gasoline");
		carTest.setMileage(11);
		carTest.setNewCar(true);
		carTest.setTitle("ciao");
		carTest.setPrice(11);
		Optional<Car> carOptional = Optional.of(carTest);
		
		given(carRepository.findById(1L)).willReturn(carOptional);
		
		Car carService = carServiceImpl.find(1L);
		
		assertThat(carService).isEqualTo(carTest);
	}
	
	@Test
	public void findBySortColumnTest() {
		Car car1 = new Car();
		car1.setId(1L);
		car1.setFuel("gasoline");
		car1.setMileage(11);
		car1.setNewCar(true);
		car1.setTitle("ciao");
		car1.setPrice(11);
		
		Car car2 = new Car();
		car2.setId(2L);
		car2.setFuel("diesel");
		car2.setMileage(12);
		car2.setNewCar(true);
		car2.setTitle("ruben");
		car2.setPrice(12);
		
		List<Car> cars = Lists.newArrayList(car1, car2);
		
		 given(carRepository.findAll(new Sort(Direction.ASC, "id"))).willReturn(cars);
		 
		 List<Car> carsReturned = carServiceImpl.find("id");
		 
		 assertThat(carsReturned).isEqualTo(cars);
		
	}
	
	@Test
	public void saveTest() {
		Car carTest = new Car();
		carTest.setId(1L);
		carTest.setFuel("gasoline");
		carTest.setMileage(11);
		carTest.setNewCar(true);
		carTest.setTitle("ciao");
		carTest.setPrice(11);
		
		carServiceImpl.save(carTest);
		
		verify(carRepository).save(carTest);
	}
	
	@Test
	public void deleteTest() {
		carServiceImpl.delete(0L);
		verify(carRepository).deleteById(0L);
	}

}
