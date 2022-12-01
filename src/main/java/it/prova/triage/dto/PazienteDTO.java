package it.prova.triage.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.triage.model.Paziente;
import it.prova.triage.model.StatoPaziente;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PazienteDTO {

	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private LocalDate dataRegistrazione;
	private StatoPaziente stato;
	private String codiceDottore;

	public PazienteDTO() {
		super();
	}

	public PazienteDTO(Long id, String nome, String cognome, String codiceFiscale, LocalDate dataRegistrazione,
			StatoPaziente stato) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataRegistrazione = dataRegistrazione;
		this.stato = stato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public LocalDate getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(LocalDate dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public StatoPaziente getStato() {
		return stato;
	}

	public void setStato(StatoPaziente stato) {
		this.stato = stato;
	}

	public String getCodiceDottore() {
		return codiceDottore;
	}

	public void setCodiceDottore(String codiceDottore) {
		this.codiceDottore = codiceDottore;
	}

	public Paziente buildPazienteModel() {
		return new Paziente(this.id, this.nome, this.cognome, this.codiceFiscale, this.dataRegistrazione, this.stato);
	}

	public static PazienteDTO buildPazienteDTOFromModel(Paziente pazienteModel) {
		return new PazienteDTO(pazienteModel.getId(), pazienteModel.getNome(), pazienteModel.getCognome(),
				pazienteModel.getCodiceFiscale(), pazienteModel.getDataRegistrazione(), pazienteModel.getStato());
	}

	public static List<PazienteDTO> createPazienteDTOListFromModelList(List<Paziente> modelListInput) {
		return modelListInput.stream()
				.map(pazienteModel -> new PazienteDTO(pazienteModel.getId(), pazienteModel.getNome(),
						pazienteModel.getCognome(), pazienteModel.getCodiceFiscale(),
						pazienteModel.getDataRegistrazione(), pazienteModel.getStato()))
				.collect(Collectors.toList());
	}

}
