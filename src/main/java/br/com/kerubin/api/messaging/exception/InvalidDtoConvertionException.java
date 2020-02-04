package br.com.kerubin.api.messaging.exception;

public class InvalidDtoConvertionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidDtoConvertionException(String message) {
		super(message);
	}


}
