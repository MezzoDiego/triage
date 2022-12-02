package it.prova.triage.web.api.exceptions;

public class HttpErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HttpErrorException() {

	}

	public HttpErrorException(String message) {
		super(message);
	}

}
