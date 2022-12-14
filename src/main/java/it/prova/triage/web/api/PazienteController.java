package it.prova.triage.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import it.prova.triage.dto.DottoreRequestDTO;
import it.prova.triage.dto.DottoreResponseDTO;
import it.prova.triage.dto.PazienteDTO;
import it.prova.triage.model.Paziente;
import it.prova.triage.service.PazienteService;
import it.prova.triage.web.api.exceptions.DottoreNotAvailableException;
import it.prova.triage.web.api.exceptions.HttpErrorException;
import it.prova.triage.web.api.exceptions.IdNotNullForInsertException;
import it.prova.triage.web.api.exceptions.NotFoundException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/paziente")
public class PazienteController {

	@Autowired
	private PazienteService pazienteService;

	@Autowired
	private WebClient webClient;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PazienteDTO inserisci(@RequestBody PazienteDTO paziente) {
		if (paziente.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		return PazienteDTO.buildPazienteDTOFromModel(pazienteService.inserisciNuovo(paziente.buildPazienteModel()));
	}

	@GetMapping
	public List<PazienteDTO> listAll() {
		return PazienteDTO.createPazienteDTOListFromModelList(pazienteService.listAllPazienti());
	}

	@GetMapping("/{id}")
	public PazienteDTO findById(@PathVariable(required = true) Long id) {
		Paziente pazienteDaCaricare = pazienteService.caricaSingoloPaziente(id);

		if (pazienteDaCaricare == null)
			throw new NotFoundException("Paziente non trovato con id: " + id);

		return PazienteDTO.buildPazienteDTOFromModel(pazienteDaCaricare);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) {
		Paziente pazienteDaEliminare = pazienteService.caricaSingoloPaziente(id);

		if (pazienteDaEliminare == null)
			throw new NotFoundException("Paziente non trovato con id: " + id);

		pazienteService.rimuovi(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public PazienteDTO update(@Valid @RequestBody PazienteDTO paziente, @PathVariable(required = true) Long id) {
		Paziente Paziente = pazienteService.caricaSingoloPaziente(id);

		if (Paziente == null)
			throw new NotFoundException("Paziente not found con id: " + id);

		paziente.setId(id);
		Paziente pazienteAggiornato = pazienteService.aggiorna(paziente.buildPazienteModel());
		return PazienteDTO.buildPazienteDTOFromModel(pazienteAggiornato);
	}

	@PostMapping("/ricovera/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public DottoreResponseDTO ricovera(@Valid @RequestBody DottoreRequestDTO dottoreRequest,
			@PathVariable(required = true) Long id) {

		pazienteService.ricovera(id);

		ResponseEntity<DottoreResponseDTO> response = webClient.post().uri("/ricovera")
				.body(Mono.just(new DottoreRequestDTO(dottoreRequest.getCodiceDottore(),
						dottoreRequest.getCodiceFiscalePazienteAttualmenteInVisita())), DottoreRequestDTO.class)
				.retrieve().onStatus(HttpStatus::is4xxClientError, response1 ->{throw new HttpErrorException("si e' verificato un errore nell'app dottore."); }).toEntity(DottoreResponseDTO.class).block();

		return new DottoreResponseDTO(response.getBody().getNome(), response.getBody().getCognome(),
				response.getBody().getCodiceDottore(), response.getBody().getInServizio(),
				response.getBody().getInVisita());

	}

	@PostMapping("/dimetti/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public DottoreResponseDTO dimetti(@Valid @RequestBody DottoreRequestDTO dottoreRequest,
			@PathVariable(required = true) Long id) {

		pazienteService.dimetti(id);

		ResponseEntity<DottoreResponseDTO> response = webClient.post().uri("/ricovera")
				.body(Mono.just(new DottoreRequestDTO(dottoreRequest.getCodiceDottore(),
						dottoreRequest.getCodiceFiscalePazienteAttualmenteInVisita())), DottoreRequestDTO.class)
				.retrieve().onStatus(HttpStatus::is4xxClientError, response1 ->{throw new HttpErrorException("si e' verificato un errore nell'app dottore."); }).toEntity(DottoreResponseDTO.class).block();

		return new DottoreResponseDTO(response.getBody().getNome(), response.getBody().getCognome(),
				response.getBody().getCodiceDottore(), response.getBody().getInServizio(),
				response.getBody().getInVisita());

	}

}
