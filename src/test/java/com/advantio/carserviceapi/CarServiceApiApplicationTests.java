package com.advantio.carserviceapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import com.advantio.carserviceapi.domain.Car;
import com.advantio.carserviceapi.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarServiceApiApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	CarService carService;

	private TestRestTemplate restTemplate;
	private HttpHeaders headers;

	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void setUp() {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		restTemplate = new TestRestTemplate();
		restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		headers = new HttpHeaders();
		headers.setOrigin("https://stackoverflow.com");
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	
	@Test
	public void saveCarTest() throws IOException {
		Car carTest = new Car();
		carTest.setFuel("gasoline");
		carTest.setNewCar(true);
		carTest.setTitle("ciao");
		carTest.setPrice(11);
		
		HttpEntity<String> entity = new HttpEntity<String>(jacksonObjectMapper.writeValueAsString(carTest), headers);
		
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/cars"), HttpMethod.POST, entity,
				String.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(jacksonObjectMapper.readValue(response.getBody(), Car.class).getId()).isNotNull();
	}

	@Test
	public void findCarsTest() throws IOException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/cars"), HttpMethod.GET, entity,
				String.class);

		List<Car> cars = carService.find("id");

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(jacksonObjectMapper.writeValueAsString(cars));
	}


	@Test
	public void findCarTest() throws IOException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		Car car = carService.find("id").get(0);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/cars/" + car.getId()),
				HttpMethod.GET, entity, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(jacksonObjectMapper.readValue(response.getBody(), Car.class)).isEqualTo(car);
	}

	@Test
	public void updateCarTest() throws IOException {
		Car carTest = carService.find("id").get(0);

		HttpEntity<String> entity = new HttpEntity<String>(jacksonObjectMapper.writeValueAsString(carTest), headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/cars/" + carTest.getId()),
				HttpMethod.PATCH, entity, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(jacksonObjectMapper.readValue(response.getBody(), Car.class)).isEqualTo(carTest);
	}

	@Test
	public void deleteCarTest() throws IOException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		List<Car> cars = carService.find("id");
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/cars/" + cars.size() + 1), HttpMethod.DELETE,
				entity, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	public void contextLoads() {
	}

}
