package de.adorsys.tanserver.service;

public class TANServerSystemException extends RuntimeException {

	public TANServerSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public TANServerSystemException(String message) {
		super(message);
	}

	public TANServerSystemException(Throwable cause) {
		super(cause);
	}
	

}
