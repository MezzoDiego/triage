package it.prova.triage.dto;

import javax.validation.constraints.NotBlank;


public class DottoreRequestDTO {
	@NotBlank(message = "{codiceDottore.notblank}")
	private String codiceDottore;
	@NotBlank(message = "{codicefiscale.notblank}")
	private String codiceFiscalePazienteAttualmenteInVisita;

	public DottoreRequestDTO() {
		super();
	}

	public DottoreRequestDTO(String codiceDottore, String codiceFiscalePazienteAttualmenteInVisita) {
		super();
		this.codiceDottore = codiceDottore;
		this.codiceFiscalePazienteAttualmenteInVisita = codiceFiscalePazienteAttualmenteInVisita;
	}

	public String getCodiceDottore() {
		return codiceDottore;
	}

	public void setCodiceDottore(String codiceDottore) {
		this.codiceDottore = codiceDottore;
	}

	public String getCodiceFiscalePazienteAttualmenteInVisita() {
		return codiceFiscalePazienteAttualmenteInVisita;
	}

	public void setCodiceFiscalePazienteAttualmenteInVisita(String codiceFiscalePazienteAttualmenteInVisita) {
		this.codiceFiscalePazienteAttualmenteInVisita = codiceFiscalePazienteAttualmenteInVisita;
	}

}
