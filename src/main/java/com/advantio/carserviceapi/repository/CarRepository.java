package com.advantio.carserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.advantio.carserviceapi.domain.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

}
