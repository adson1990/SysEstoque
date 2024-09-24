package com.adsonlucas.SysEstoque.resources;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.services.ClientService;
import com.adsonlucas.SysEstoque.services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientService clientService;
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll(
	@RequestParam(value = "page", defaultValue = "0") Integer page,
	@RequestParam(value = "linesPerPage", defaultValue = "7") Integer linesPerPage,
	@RequestParam(value = "direction", defaultValue = "ASC") String direction,
	@RequestParam(value = "orderBy", defaultValue = "ID") String orderBY
	){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBY);
		
		Page<ClientDTO> list = clientService.findAllPages(pageRequest);
	
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{ID}")
	public ResponseEntity<ClientDTO> findClientById(@PathVariable @NotNull @Positive Long ID){
		ClientDTO clientDTO = clientService.findById(ID);
	
		return ResponseEntity.ok().body(clientDTO);
	}
	
	@GetMapping(value = "/email/{email}")
	public boolean findClientByEmail(@PathVariable @NotNull String email){
		logger.info("Received request for email: " + email);
		ClientDTO clientDTO = clientService.findByEmail(email);
	
		return clientDTO != null;
	}
	
	// Insert
	@PostMapping(value = "/register")
	public ResponseEntity<ClientDTO> insertClient(@Valid @RequestBody ClientDTO dto){
		dto = clientService.insClient(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getID()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	// Update
	@PutMapping(value = "/{ID}")
	public ResponseEntity<ClientDTO> updateClient(@Valid @PathVariable Long ID, @RequestBody ClientDTO dto) {
		dto = clientService.updClient(dto, ID);
		
		return ResponseEntity.ok().body(dto);
	}
	
	//DELETE
	@DeleteMapping(value = "/{ID}")
	public ResponseEntity<String> deleteClient(@PathVariable @NotNull @Positive Long ID) {
		try {
			  clientService.delClient(ID);
			  return ResponseEntity.ok("Cliente com o ID " + ID +" deletado com sucesso!");
		}catch(EntidadeNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}catch (DataIntegrityViolationException d) {
			throw new DataBaseException("Violação de integridade do DB");
		}

	}

}
