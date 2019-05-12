package com.advantio.carserviceapi.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.advantio.carserviceapi.domain.ErrorDetails;
import com.advantio.carserviceapi.exception.CarNotFoundException;

@ControllerAdvice
@RestController
public class CarServiceApiExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger(CarServiceApiExceptionHandler.class);

	@ExceptionHandler(CarNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleCarNotFoundException(CarNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<ErrorDetails> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
		List<String> errors = ex.getBindingResult().getAllErrors().stream().map(errror -> errror.getDefaultMessage())
				.collect(Collectors.toList());
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), errors.stream().collect(Collectors.joining(" ")),
				request.getDescription(false));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public ResponseEntity<ErrorDetails> handleValidationErrors(HttpMessageNotReadableException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ErrorDetails> handleException(Exception ex, WebRequest request) {
		logger.error("Generic error: {}", ex);
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
	}

}
