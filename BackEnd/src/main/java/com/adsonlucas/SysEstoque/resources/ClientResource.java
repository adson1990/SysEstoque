package com.adsonlucas.SysEstoque.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;
import com.adsonlucas.SysEstoque.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientService clientService;
	
	//Select all
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll(
	@RequestParam(value = "page", defaultValue = "0") Integer page,
	@RequestParam(value = "linesPerPage", defaultValue = "7") Integer linesPerPage,
	@RequestParam(value = "direction", defaultValue = "ASC") String direction,
	@RequestParam(value = "orderBy", defaultValue = "name") String orderBY
	){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBY);
		
		Page<ClientDTO> list = clientService.findAllPages(pageRequest);
	
		return ResponseEntity.ok().body(list);
	}
	
	// Select By ID
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id){
		ClientDTO clientDTO = clientService.findById(id);
	
		return ResponseEntity.ok().body(clientDTO);
	}
	
	// Insert
	@PostMapping
	public ResponseEntity<ClientDTO> insertClient(@RequestBody ClientDTO dto){
		dto = clientService.insClient(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getID()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	// Update
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> updateClient(@PathVariable Long ID, @RequestBody ClientDTO dto) {
		dto = clientService.updClient(dto, ID);
		
		return ResponseEntity.ok().body(dto);
	}
	
	//DELETE
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Void> deleteClient(@PathVariable Long ID) {
		clientService.delClient(ID);
		
		return ResponseEntity.noContent().build();
	}

}
