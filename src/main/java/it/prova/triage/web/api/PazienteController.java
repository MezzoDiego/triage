package it.prova.triage.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.triage.service.PazienteService;

@RestController
@RequestMapping("api/paziente")
public class PazienteController {

	@Autowired
	private PazienteService pazienteService;

}
