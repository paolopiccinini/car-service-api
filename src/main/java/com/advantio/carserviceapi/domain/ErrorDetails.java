package com.advantio.carserviceapi.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class ErrorDetails {

	private LocalDate timestamp;
	private String message;
	private String details;

}
