package br.com.kerubin.api.messaging.exception;

public class NullOrEmptyValueException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NullOrEmptyValueException(String message) {
		super(message);
	}


}
