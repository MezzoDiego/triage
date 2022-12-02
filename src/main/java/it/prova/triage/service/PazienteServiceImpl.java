package it.prova.triage.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.triage.model.Paziente;
import it.prova.triage.model.StatoPaziente;
import it.prova.triage.repository.paziente.PazienteRepository;
import it.prova.triage.web.api.exceptions.NotFoundException;
import it.prova.triage.web.api.exceptions.NotRemovableException;
import it.prova.triage.web.api.exceptions.NullException;
import it.prova.triage.web.api.exceptions.PazienteNonInVisitaException;

@Service
@Transactional(readOnly = true)
public class PazienteServiceImpl implements PazienteService {

	@Autowired
	private PazienteRepository repository;

	@Override
	public List<Paziente> listAllPazienti() {
		return (List<Paziente>) repository.findAll();
	}

	@Override
	public Paziente caricaSingoloPaziente(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Paziente aggiorna(Paziente pazienteInstance) {
		return repository.save(pazienteInstance);
	}

	@Override
	@Transactional
	public Paziente inserisciNuovo(Paziente pazienteInstance) {
		pazienteInstance.setDataRegistrazione(LocalDate.now());
		pazienteInstance.setStato(StatoPaziente.IN_ATTESA_VISITA);
		return repository.save(pazienteInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {
		Paziente pazienteReloaded = repository.findById(idToRemove).orElse(null);
		if (pazienteReloaded == null)
			throw new NullException("Nessun Paziente trovato tramite l'id dato.");

		if (!pazienteReloaded.getStato().equals(StatoPaziente.DIMESSO))
			throw new NotRemovableException("Impossibile eliminare un paziente non dimesso.");

		repository.deleteById(idToRemove);

	}

	@Override
	public List<Paziente> findByExample(Paziente example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void impostaCodiceDottoreAPaziente(String CF, String codiceDottore) {
		Paziente pazienteReloaded = repository.findByCodiceFiscale(CF);

		if (pazienteReloaded == null)
			throw new NotFoundException("Paziente non trovato con Codice Fiscale: " + CF);

		pazienteReloaded.setCodiceDottore(codiceDottore);
		pazienteReloaded.setStato(StatoPaziente.IN_VISITA);
		repository.save(pazienteReloaded);

	}

	@Override
	@Transactional
	public void ricovera(Long id) {
		Paziente pazienteReloaded = repository.findById(id).orElse(null);

		if (pazienteReloaded == null)
			throw new NotFoundException("Paziente non trovato con id: " + id);

		if (!pazienteReloaded.getStato().equals(StatoPaziente.IN_VISITA))
			throw new PazienteNonInVisitaException("Impossibile ricoverare un paziente che non e' in visita!!!!!!");

		pazienteReloaded.setStato(StatoPaziente.RICOVERATO);
		pazienteReloaded.setCodiceDottore(null);
		repository.save(pazienteReloaded);
	}

	@Override
	@Transactional
	public void dimetti(Long id) {
		Paziente pazienteReloaded = repository.findById(id).orElse(null);

		if (pazienteReloaded == null)
			throw new NotFoundException("Paziente non trovato con id: " + id);

		if (!pazienteReloaded.getStato().equals(StatoPaziente.IN_VISITA))
			throw new PazienteNonInVisitaException("Impossibile ricoverare un paziente che non e' in visita!!!!!!");

		pazienteReloaded.setStato(StatoPaziente.DIMESSO);
		pazienteReloaded.setCodiceDottore(null);
		repository.save(pazienteReloaded);

	}

}
