package it.prova.triage.web.api.exceptions;

public class PazienteNonInVisitaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PazienteNonInVisitaException() {

	}

	public PazienteNonInVisitaException(String message) {
		super(message);
	}

}
