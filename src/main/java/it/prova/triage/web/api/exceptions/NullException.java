package it.prova.triage.web.api.exceptions;

public class NullException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullException() {

	}

	public NullException(String message) {
		super(message);
	}

}
