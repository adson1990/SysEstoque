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

import com.adsonlucas.SysEstoque.entitiesDTO.RolesDTO;
import com.adsonlucas.SysEstoque.services.RolesService;

@RestController
@RequestMapping(value = "/roles")
public class RolesResource {
	
	@Autowired
	private RolesService service;
	
	//BUSCA
	@GetMapping	
	public ResponseEntity<Page<RolesDTO>> findAll(
	@RequestParam(value = "page", defaultValue = "0")	Integer page,
	@RequestParam(value = "linesPerPage", defaultValue = "4") Integer linesPerPage,
	@RequestParam(value = "direction", defaultValue = "ASC") String direction,
	@RequestParam(value = "OrderBy", defaultValue = "ID") String OrderBy
	){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), OrderBy);
		
		Page<RolesDTO> list = service.findAllPages(pageRequest);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{ID}")
	public ResponseEntity<RolesDTO> findByID(@PathVariable Long ID){
		RolesDTO dto = service.findById(ID);
		
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<RolesDTO> insertRoles(@RequestBody RolesDTO role){
		role = service.instRoles(role);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(role.getID()).toUri();
		
		return ResponseEntity.created(uri).body(role);
	}
	
	@PutMapping(value = "/{ID}")
	public ResponseEntity<RolesDTO> updRoles(@PathVariable Long ID, @RequestBody RolesDTO dto){
		RolesDTO role = service.updRoles(ID, dto);
		
		return ResponseEntity.ok().body(role);
	}
	
	@DeleteMapping(value = "/{ID}")
	public ResponseEntity<Void> deleteRoles(@PathVariable Long ID) {
		service.delRoles(ID);
		
		return ResponseEntity.noContent().build();
	}

}
