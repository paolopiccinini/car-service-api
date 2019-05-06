package com.advantio.carserviceapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.advantio.carserviceapi.domain.Car;
import com.advantio.carserviceapi.exception.CarNotFoundException;
import com.advantio.carserviceapi.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

	@MockBean
	private CarService carService;

	@Autowired
	private MockMvc mvc;

	private JacksonTester<Car> carJackson;
	private JacksonTester<List<Car>> carsJackson;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void saveOkTest() throws Exception {
		Car carTest = new Car();
		carTest.setId(1L);
		carTest.setFuel("gasoline");
		carTest.setNewCar(true);
		carTest.setTitle("ciao");
		carTest.setPrice(11);

		given(carService.save(any(Car.class))).willReturn(carTest);

		MockHttpServletResponse response = mvc.perform(
				post("/cars").contentType(MediaType.APPLICATION_JSON).content(carJackson.write(carTest).getJson()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(carJackson.write(carTest).getJson());

	}

	@Test
	public void saveBadRequestTest() throws Exception {
		Car carTest = new Car();
		carTest.setId(1L);
		carTest.setFuel("gasoline");
		carTest.setNewCar(true);
		carTest.setTitle("ciao");
		carTest.setPrice(11);
		carTest.setMileage(11);

		MockHttpServletResponse response = mvc.perform(
				post("/cars").contentType(MediaType.APPLICATION_JSON).content(carJackson.write(carTest).getJson()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void updateOkTest() throws Exception {
		Car carTest = new Car();
		carTest.setId(1L);
		carTest.setFuel("gasoline");
		carTest.setNewCar(true);
		carTest.setTitle("ciao");
		carTest.setPrice(11);

		Car carUpdated = new Car();
		carUpdated.setId(1L);
		carUpdated.setFuel("gasoline");
		carUpdated.setNewCar(true);
		carUpdated.setTitle("ciao");
		carUpdated.setPrice(12);

		given(carService.find(any(Long.class))).willReturn(carTest);
		given(carService.save(any(Car.class))).willReturn(carUpdated);

		MockHttpServletResponse response = mvc.perform(
				put("/cars/1").contentType(MediaType.APPLICATION_JSON).content(carJackson.write(carUpdated).getJson()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(carJackson.write(carUpdated).getJson());
	}

	@Test
	public void updateNotFoundTest() throws Exception {
		Car carUpdated = new Car();
		carUpdated.setId(1L);
		carUpdated.setFuel("gasoline");
		carUpdated.setNewCar(true);
		carUpdated.setTitle("ciao");
		carUpdated.setPrice(12);

		given(carService.find(any(Long.class))).willThrow(new CarNotFoundException("Car not found with id: 1"));

		MockHttpServletResponse response = mvc.perform(
				put("/cars/1").contentType(MediaType.APPLICATION_JSON).content(carJackson.write(carUpdated).getJson()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void updateBadRequestTest() throws Exception {
		Car carTest = new Car();
		carTest.setId(1L);
		carTest.setFuel("gasoline");
		carTest.setNewCar(true);
		carTest.setTitle("ciao");
		carTest.setPrice(11);
		carTest.setMileage(11);

		MockHttpServletResponse response = mvc.perform(
				put("/cars/1").contentType(MediaType.APPLICATION_JSON).content(carJackson.write(carTest).getJson()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void deleteNotFoundTest() throws Exception {
		doThrow(new CarNotFoundException("")).when(carService).delete(1L);

		MockHttpServletResponse response = mvc.perform(delete("/cars/1").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deleteOkTest() throws Exception {
		MockHttpServletResponse response = mvc.perform(delete("/cars/1").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void findAllOkTest() throws Exception {
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

		given(carService.find("id")).willReturn(cars);

		MockHttpServletResponse response = mvc
				.perform(get("/cars?sortColumn=id").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(carsJackson.write(cars).getJson());
	}

	@Test
	public void findOkTest() throws Exception {
		Car carTest = new Car();
		carTest.setId(1L);
		carTest.setFuel("gasoline");
		carTest.setNewCar(true);
		carTest.setTitle("ciao");
		carTest.setPrice(11);
		carTest.setMileage(11);

		given(carService.find(any(Long.class))).willReturn(carTest);

		MockHttpServletResponse response = mvc.perform(get("/cars/1").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(carJackson.write(carTest).getJson());
	}

	@Test
	public void findNotFoundTest() throws Exception {
		given(carService.find(any(Long.class))).willThrow(new CarNotFoundException("Car not found with id: 1"));

		MockHttpServletResponse response = mvc.perform(get("/cars/1").contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

}
