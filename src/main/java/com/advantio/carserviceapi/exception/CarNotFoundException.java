package com.advantio.carserviceapi.exception;

public class CarNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4985659349321280748L;

	public CarNotFoundException(String exception) {
		super(exception);
	}

}
