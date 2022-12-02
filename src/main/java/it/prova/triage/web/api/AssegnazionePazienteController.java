package it.prova.triage.web.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import it.prova.triage.dto.DottoreRequestDTO;
import it.prova.triage.dto.DottoreResponseDTO;
import it.prova.triage.service.PazienteService;
import it.prova.triage.web.api.exceptions.DottoreNotAvailableException;
import it.prova.triage.web.api.exceptions.NotFoundException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/assegnaPaziente")
public class AssegnazionePazienteController {

	@Autowired
	private PazienteService pazienteService;
	
	@Autowired
	private WebClient webClient;
	
	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public DottoreResponseDTO impostaVisita( @Valid @RequestBody DottoreRequestDTO dottoreRequest) {
		
		DottoreResponseDTO dottoreResponseDTO = webClient.get().uri("/codiceDottore/" + dottoreRequest.getCodiceDottore()).retrieve()
				.bodyToMono(DottoreResponseDTO.class).block();
		
		if(dottoreResponseDTO == null)
			throw new NotFoundException("Dottore non trovato con codice: " + dottoreRequest.getCodiceDottore());
		
		if(dottoreResponseDTO.getInVisita() == true || dottoreResponseDTO.getInServizio() == false)
			throw new DottoreNotAvailableException();
		
		pazienteService.impostaCodiceDottoreAPaziente(dottoreRequest.getCodiceFiscalePazienteAttualmenteInVisita(), dottoreRequest.getCodiceDottore());
		
		ResponseEntity<DottoreResponseDTO> response = webClient
				.post().uri("/impostaInVisita").body(
						Mono.just(new DottoreRequestDTO(dottoreRequest.getCodiceDottore(), dottoreRequest.getCodiceFiscalePazienteAttualmenteInVisita())),
						DottoreRequestDTO.class)
				.retrieve().toEntity(DottoreResponseDTO.class).block();
		
		return new DottoreResponseDTO(response.getBody().getNome(), response.getBody().getCognome(), response.getBody().getCodiceDottore(), response.getBody().getInServizio(), response.getBody().getInVisita());
		
	}
	
}
