package it.prova.triage.service;

import java.util.List;

import it.prova.triage.model.Paziente;

public interface PazienteService {
	public List<Paziente> listAllPazienti();

	public Paziente caricaSingoloPaziente(Long id);

	public Paziente aggiorna(Paziente pazienteInstance);

	public Paziente inserisciNuovo(Paziente pazienteInstance);

	public void rimuovi(Long idToRemove);

	public List<Paziente> findByExample(Paziente example);
	
	public void impostaCodiceDottoreAPaziente(String CF, String CodiceDottore);
	
	public void ricovera(Long id);
	
	public void dimetti(Long id);
}
