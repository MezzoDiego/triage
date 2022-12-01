package it.prova.triage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.triage.model.Ruolo;
import it.prova.triage.repository.ruolo.RuoloRepository;

@Service
@Transactional(readOnly = true)
public class RuoloServiceImpl implements RuoloService {

	@Autowired
	private RuoloRepository repository;

	@Override
	public List<Ruolo> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ruolo caricaSingoloElemento(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void aggiorna(Ruolo ruoloInstance) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void inserisciNuovo(Ruolo ruoloInstance) {
		repository.save(ruoloInstance);

	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {
		// TODO Auto-generated method stub

	}

	@Override
	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice) {
		return repository.findByDescrizioneAndCodice(descrizione, codice);
	}

}
