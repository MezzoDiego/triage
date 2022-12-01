package it.prova.triage.web.api.exceptions;

public class NotRemovableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotRemovableException() {

	}

	public NotRemovableException(String message) {
		super(message);
	}

}
