package com.adsonlucas.SysEstoque.resources; 


import java.net.URI;

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

import com.adsonlucas.SysEstoque.entitiesDTO.UserDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	//BUSCA
	@GetMapping	
	public ResponseEntity<Page<UserDTO>> findAll(
	@RequestParam(value = "page", defaultValue = "0")	Integer page,
	@RequestParam(value = "linesPerPage", defaultValue = "4") Integer linesPerPage,
	@RequestParam(value = "direction", defaultValue = "ASC") String direction,
	@RequestParam(value = "orderBy", defaultValue = "ID") String orderBY
	){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBY);
		
		Page<UserDTO> list = service.findAllPages(pageRequest);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{ID}")
	public ResponseEntity<UserDTO> findByID(@PathVariable @NotNull @Positive Long ID){
		UserDTO dto = service.findById(ID);
		
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insertUser(@Valid @RequestBody UserDTO user){
		user = service.instUser(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getID()).toUri();
		
		return ResponseEntity.created(uri).body(user);
	}
	
	@PutMapping(value = "/{ID}")
	public ResponseEntity<UserDTO> updUser(@Valid @PathVariable Long ID, @RequestBody UserDTO dto){
		UserDTO user = service.updUser(ID, dto);
		
		return ResponseEntity.ok().body(user);
	}
	
	@DeleteMapping(value = "/{ID}")
	public ResponseEntity<String> deleteUser(@PathVariable @NotNull @Positive Long ID) {
		try {
			  service.delUser(ID);
			  return ResponseEntity.ok("Usuário com o ID " + ID +" deletado com sucesso!");
		}catch(EntidadeNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}catch (DataIntegrityViolationException d) {
			throw new DataBaseException("Violação de integridade do DB");
		}
		
		//return ResponseEntity.noContent().build();
	}

}
