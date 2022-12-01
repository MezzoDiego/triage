package it.prova.triage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.triage.model.Paziente;
import it.prova.triage.repository.paziente.PazienteRepository;

@Service
@Transactional(readOnly = true)
public class PazienteServiceImpl implements PazienteService {

	@Autowired
	private PazienteRepository repository;

	@Override
	public List<Paziente> listAllPazienti() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paziente caricaSingoloPaziente(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Paziente aggiorna(Paziente pazienteInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Paziente inserisciNuovo(Paziente pazienteInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Paziente> findByExample(Paziente example) {
		// TODO Auto-generated method stub
		return null;
	}

}
