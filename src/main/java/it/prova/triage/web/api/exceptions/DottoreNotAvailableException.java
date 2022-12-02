package it.prova.triage.web.api.exceptions;

public class DottoreNotAvailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DottoreNotAvailableException() {

	}

	public DottoreNotAvailableException(String message) {
		super(message);
	}

}
